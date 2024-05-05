package com.whatmovie.app.compose.data.remote.responses

import com.whatmovie.app.compose.data.remote.services.NetworkObserver
import com.whatmovie.app.compose.domain.models.NoInternetThrowable
import retrofit2.HttpException
import retrofit2.Response

sealed interface NetworkResult<T : Any> {
    class Success<T : Any>(val data: T) : NetworkResult<T>
    class Error<T : Any>(val code: Int, val message: String?) : NetworkResult<T>
    class Exception<T : Any>(val e: Throwable) : NetworkResult<T>
}


suspend fun <T : Any> handleApi(
    execute: suspend () -> Response<T>
): NetworkResult<T> {
    return try {
        val response = execute()
        val body = response.body()
        if (response.isSuccessful && body != null) {
            NetworkResult.Success(body)
        } else {
            NetworkResult.Error(code = response.code(), message = response.message())
        }
    } catch (e: HttpException) {
        NetworkResult.Error(code = e.code(), message = e.message())
    } catch (e: Throwable) {
        if (NetworkObserver.isNetworkConnected) {
            NetworkResult.Exception(e)
        } else {
            NetworkResult.Exception(NoInternetThrowable)
        }
    }
}