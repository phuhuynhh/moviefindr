package com.whatmovie.app.compose.domain.models

import com.whatmovie.app.compose.domain.exceptions.DataError


const val HTTP_ERROR_REQUEST_TIMEOUT = 408


object NoInternetThrowable: Throwable()
object NoInternetException: Exception("Internet Unavailable")


object NetworkErrorHandler {
    fun exception(exception: Throwable): DataError.Network {
        return when (exception) {
            is NoInternetThrowable -> DataError.Network.NO_INTERNET
            else -> {
                DataError.Network.UNKNOWN
            }
        }
    }

    fun errorCode(httpErrorCode: Int): DataError.Network {
        return when (httpErrorCode) {
            HTTP_ERROR_REQUEST_TIMEOUT -> {
                DataError.Network.REQUEST_TIMEOUT
            }
            else -> {
                DataError.Network.UNKNOWN
            }
        }
    }
}