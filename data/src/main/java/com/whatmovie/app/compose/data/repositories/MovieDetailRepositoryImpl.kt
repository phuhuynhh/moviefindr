package com.whatmovie.app.compose.data.repositories

import com.whatmovie.app.compose.data.database.model.toInfo
import com.whatmovie.app.compose.data.extensions.flowTransform
import com.whatmovie.app.compose.data.remote.responses.NetworkResult
import com.whatmovie.app.compose.data.remote.responses.handleApi
import com.whatmovie.app.compose.data.remote.services.ApiService
import com.whatmovie.app.compose.domain.DispatchersProvider
import com.whatmovie.app.compose.domain.exceptions.DataError
import com.whatmovie.app.compose.domain.models.DataResult
import com.whatmovie.app.compose.domain.models.MovieDetailInfo
import com.whatmovie.app.compose.domain.models.NetworkErrorHandler
import com.whatmovie.app.compose.domain.repositories.MovieDetailRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieDetailRepositoryImpl @Inject constructor(
    private val movieDetailStorage: MovieDetailStorage,
    private val apiService: ApiService,
    private val dispatchersProvider: DispatchersProvider,
) : MovieDetailRepository {
    override fun getMovies(movieId: Long): Flow<DataResult<MovieDetailInfo, DataError>> = flowTransform {
        val localData = movieDetailStorage.getMovieDetail(movieId)
        if (localData != null) {
            DataResult.Success(localData.toInfo())
        } else {
            fetchRemoteMovieDetail(movieId)
        }
    }

    private suspend fun fetchRemoteMovieDetail(movieId: Long): DataResult<MovieDetailInfo, DataError.Network> {
        val remoteData = handleApi { apiService.getMovieDetail(movieId) }
        return when (remoteData) {
            is NetworkResult.Error -> DataResult.Error(NetworkErrorHandler.errorCode(remoteData.code))
            is NetworkResult.Exception -> DataResult.Error(NetworkErrorHandler.exception(remoteData.e))
            is NetworkResult.Success -> {
                movieDetailStorage.saveMovieDetail(remoteData.data)
                DataResult.Success(remoteData.data.toInfo())
            }
        }

    }
}

