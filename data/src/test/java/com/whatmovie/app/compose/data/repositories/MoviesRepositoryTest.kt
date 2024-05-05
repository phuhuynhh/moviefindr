package com.whatmovie.app.compose.data.repositories

import com.whatmovie.app.compose.data.database.model.toInfo
import com.whatmovie.app.compose.data.remote.services.ApiService
import com.whatmovie.app.compose.data.test.MockUtil
import com.whatmovie.app.compose.data.test.TestDispatchersProvider
import com.whatmovie.app.compose.domain.models.DataResult
import com.whatmovie.app.compose.domain.models.PaginatedList
import com.whatmovie.app.compose.domain.repositories.MoviesRepository
import com.whatmovie.app.compose.domain.usecases.GetMoviesUseCase
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import retrofit2.Response

@ExperimentalCoroutinesApi
class MoviesRepositoryTest {

    private lateinit var mockService: ApiService
    private lateinit var storage: MoviesStorage
    private lateinit var moviesRepository: MoviesRepository

    private lateinit var getMoviesUseCase: GetMoviesUseCase

    @Before
    fun setUp() {
        mockService = mockk()
        storage = mockk()
        moviesRepository = MoviesRepositoryImpl(storage, mockService, TestDispatchersProvider)
        getMoviesUseCase = GetMoviesUseCase(moviesRepository)

    }

    @Test
    fun `When request local successful, it returns success`() = runTest {
        val expected = MockUtil.moviePaginated
        coEvery { storage.getMoviesByPage() } returns MockUtil.moviePaginated

        moviesRepository.getMovies().collect {
            it shouldBe DataResult.Success(
                PaginatedList(
                    page = expected.page,
                    results = expected.results.map { movie -> movie.toInfo() },
                    totalPages = expected.totalPages,
                    totalResults = expected.totalResults
                )
            )
        }
    }

    @Test
    fun `When request remote successful, it returns success`() = runTest {
        val expected = MockUtil.moviePaginated
        coEvery { storage.getMoviesByPage() } returns null
        coEvery { storage.saveMovies(any(), any()) } coAnswers { }
        coEvery { mockService.getTrendingMovies() } returns Response.success(expected)

        moviesRepository.getMovies().collect {
            it shouldBe DataResult.Success(
                PaginatedList(
                    page = expected.page,
                    results = expected.results.map { movie -> movie.toInfo() },
                    totalPages = expected.totalPages,
                    totalResults = expected.totalResults
                )
            )
        }
    }

    @Test
    fun `When request failed, it returns error`() = runTest {
        val expected = Throwable()
        coEvery { mockService.getTrendingMovies() } throws expected

        moviesRepository.getMovies().catch {
            it shouldBe expected
        }
    }
}
