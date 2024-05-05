package com.whatmovie.app.compose.domain.usecases

import com.whatmovie.app.compose.domain.exceptions.DataError
import com.whatmovie.app.compose.domain.models.DataResult
import com.whatmovie.app.compose.domain.models.MovieDetailInfo
import com.whatmovie.app.compose.domain.repositories.MovieDetailRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMovieDetailUseCase @Inject constructor(private val movieDetailRepository: MovieDetailRepository) {

    operator fun invoke(movieId: Long):  Flow<DataResult<MovieDetailInfo, DataError>>  {
        return movieDetailRepository.getMovies(movieId = movieId)
    }
}
