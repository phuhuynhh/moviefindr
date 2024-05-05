package com.whatmovie.app.compose.domain.test

import com.whatmovie.app.compose.domain.exceptions.DataError
import com.whatmovie.app.compose.domain.models.DataResult
import com.whatmovie.app.compose.domain.models.MovieInfo
import com.whatmovie.app.compose.domain.models.PaginatedList

object MockUtil {

    val movieInfos = PaginatedList(
        page = 1,
        listOf(
            MovieInfo(
                id = 1000,
                backdropPath = "https://image.tmdb.org/t/p/w500/or06FN3Dka5tukK1e9sl16pB3iy.jpg",
                originalTitle = "",
                overview = "",
                posterPath = "",
                adult = false,
                title = "Avengers: Endgame",
                originalLanguage = "A",
                genreIds = listOf(),
                releaseDate = "2019-04-24",
                popularity = 100.0,
                video = false,
                voteAverage = 8.255,
                voteCount = 100000,
            ),
        ),
        totalPages = 1,
        totalResults = 1,
    )

    val successMovieInfoDataResult: DataResult<PaginatedList<MovieInfo>, DataError> = DataResult.Success(movieInfos)
    val errorMovieInfoDataResult: DataResult<PaginatedList<MovieInfo>, DataError> = DataResult.Error(DataError.Network.UNKNOWN)
}
