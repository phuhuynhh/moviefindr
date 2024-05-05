package com.whatmovie.app.compose.domain.usecases

import com.whatmovie.app.compose.domain.exceptions.DataError
import com.whatmovie.app.compose.domain.models.DataResult
import com.whatmovie.app.compose.domain.models.MovieInfo
import com.whatmovie.app.compose.domain.models.PaginatedList
import com.whatmovie.app.compose.domain.repositories.MoviesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMoviesUseCase @Inject constructor(private val moviesRepository: MoviesRepository) {

    operator fun invoke(searchQuery: String = "", currentPage: Int = 1): Flow<DataResult<PaginatedList<MovieInfo>, DataError>> {
        return moviesRepository.getMovies(searchQuery, currentPage)
    }
}
