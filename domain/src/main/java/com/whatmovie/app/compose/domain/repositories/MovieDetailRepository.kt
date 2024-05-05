package com.whatmovie.app.compose.domain.repositories

import com.whatmovie.app.compose.domain.exceptions.DataError
import com.whatmovie.app.compose.domain.models.DataResult
import com.whatmovie.app.compose.domain.models.MovieDetailInfo
import kotlinx.coroutines.flow.Flow

interface MovieDetailRepository {
    fun getMovies(movieId: Long): Flow<DataResult<MovieDetailInfo, DataError>>
}