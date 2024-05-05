package com.whatmovie.app.compose.domain.models

import com.whatmovie.app.compose.domain.exceptions.Error

typealias RootError = Error

sealed interface DataResult<out D, out E: RootError> {
    data class Success<out D, out E: RootError>(val data: D): DataResult<D, E>
    data class Error<out D, out E: RootError>(val error: E): DataResult<D, E>
}