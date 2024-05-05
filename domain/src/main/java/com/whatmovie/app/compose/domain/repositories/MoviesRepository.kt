package com.whatmovie.app.compose.domain.repositories

import com.whatmovie.app.compose.domain.exceptions.DataError
import com.whatmovie.app.compose.domain.models.DataResult
import com.whatmovie.app.compose.domain.models.MovieInfo
import com.whatmovie.app.compose.domain.models.PaginatedList
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {
    fun getMovies(
        searchQuery: String = "",
        page: Int = 1
    ): Flow<DataResult<PaginatedList<MovieInfo>, DataError>>

    suspend fun getTrendingMovies(page: Int = 1): DataResult<PaginatedList<MovieInfo>, DataError>

    suspend fun fetchRemoteTrending(page: Int): DataResult<PaginatedList<MovieInfo>, DataError>

    suspend fun searchMovies(
        searchQuery: String = "",
        page: Int = 1
    ): DataResult<PaginatedList<MovieInfo>, DataError>
}
