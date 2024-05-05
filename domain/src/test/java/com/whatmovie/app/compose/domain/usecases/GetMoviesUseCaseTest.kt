package com.whatmovie.app.compose.domain.usecases

import com.whatmovie.app.compose.domain.repositories.MoviesRepository
import com.whatmovie.app.compose.domain.test.MockUtil
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class GetMoviesUseCaseTest {

    private lateinit var mockMoviesRepository: MoviesRepository
    private lateinit var getMoviesUseCase: GetMoviesUseCase

    @Before
    fun setUp() {
        mockMoviesRepository = mockk()
        getMoviesUseCase = GetMoviesUseCase(mockMoviesRepository)
    }

    @Test
    fun `When request successful, it returns success`() = runTest {
        val expected = MockUtil.successMovieInfoDataResult
        every { mockMoviesRepository.getMovies() } returns flowOf(expected)

        getMoviesUseCase().collect {
            it shouldBe expected
        }
    }

    @Test
    fun `When request failed, it returns error`() = runTest {
        val expected = MockUtil.errorMovieInfoDataResult
        every { mockMoviesRepository.getMovies() } returns flowOf(expected)

        getMoviesUseCase().collect {
            it shouldBe expected
        }
    }
}
