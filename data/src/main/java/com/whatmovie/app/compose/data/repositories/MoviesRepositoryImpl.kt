package com.whatmovie.app.compose.data.repositories

import com.whatmovie.app.compose.data.database.model.toInfo
import com.whatmovie.app.compose.data.extensions.flowTransform
import com.whatmovie.app.compose.data.remote.responses.NetworkResult
import com.whatmovie.app.compose.data.remote.responses.handleApi
import com.whatmovie.app.compose.data.remote.services.ApiService
import com.whatmovie.app.compose.domain.DispatchersProvider
import com.whatmovie.app.compose.domain.exceptions.DataError
import com.whatmovie.app.compose.domain.models.DataResult
import com.whatmovie.app.compose.domain.models.MovieInfo
import com.whatmovie.app.compose.domain.models.NetworkErrorHandler
import com.whatmovie.app.compose.domain.models.PaginatedList
import com.whatmovie.app.compose.domain.repositories.MoviesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class MoviesRepositoryImpl @Inject constructor(
    private val moviesStorage: MoviesStorage,
    private val apiService: ApiService,
    private val dispatchersProvider: DispatchersProvider,
) : MoviesRepository {
    override fun getMovies(searchQuery: String, page: Int): Flow<DataResult<PaginatedList<MovieInfo>, DataError>> =
        flowTransform {
            if (searchQuery.isEmpty()) {
                getTrendingMovies(page)
            } else {
                searchMovies(searchQuery, page)
            }
        }.flowOn(dispatchersProvider.io)

    override suspend fun getTrendingMovies(page: Int): DataResult<PaginatedList<MovieInfo>, DataError> {
        val data = moviesStorage.getMoviesByPage(page)
        return if (data != null) {
            DataResult.Success(
                PaginatedList(
                    page = data.page,
                    results = data.results.map {
                        it.toInfo()
                    },
                    totalPages = data.totalPages,
                    totalResults = data.totalResults
                )
            )
        } else {
            fetchRemoteTrending(page)
        }
    }


    override suspend fun fetchRemoteTrending(page: Int): DataResult<PaginatedList<MovieInfo>, DataError> {
        val remoteData = handleApi { apiService.getTrendingMovies(page) }
        val result = when (remoteData) {
            is NetworkResult.Error -> DataResult.Error(NetworkErrorHandler.errorCode(remoteData.code))
            is NetworkResult.Exception -> DataResult.Error(NetworkErrorHandler.exception(remoteData.e))
            is NetworkResult.Success -> {
                moviesStorage.saveMovies(remoteData.data.results, page)
                DataResult.Success<PaginatedList<MovieInfo>, DataError>(
                    PaginatedList(
                        page = remoteData.data.page,
                        results = remoteData.data.results.map {
                            it.toInfo()
                        },
                        totalPages = remoteData.data.totalPages,
                        totalResults = remoteData.data.totalResults
                    )
                )
            }
        }
        return result

    }

    override suspend fun searchMovies(searchQuery: String, page: Int): DataResult<PaginatedList<MovieInfo>, DataError> {
        val remoteData = handleApi { apiService.searchMovies(searchQuery, page) }
        val result = when (remoteData) {
            is NetworkResult.Error -> DataResult.Error(NetworkErrorHandler.errorCode(remoteData.code))
            is NetworkResult.Exception -> DataResult.Error(NetworkErrorHandler.exception(remoteData.e))
            is NetworkResult.Success -> {
                DataResult.Success<PaginatedList<MovieInfo>, DataError>(
                    PaginatedList(
                        page = remoteData.data.page,
                        results = remoteData.data.results.map {
                            it.toInfo()
                        },
                        totalPages = remoteData.data.totalPages,
                        totalResults = remoteData.data.totalResults
                    )
                )
            }
        }
        return result
    }

}

