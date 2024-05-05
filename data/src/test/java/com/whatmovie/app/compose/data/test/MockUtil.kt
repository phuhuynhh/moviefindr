package com.whatmovie.app.compose.data.test

import com.whatmovie.app.compose.data.database.model.Movie
import com.whatmovie.app.compose.data.database.model.toInfo
import com.whatmovie.app.compose.domain.exceptions.DataError
import com.whatmovie.app.compose.domain.models.DataResult
import com.whatmovie.app.compose.domain.models.MovieInfo
import com.whatmovie.app.compose.domain.models.PaginatedList

object MockUtil {

    val movieList = listOf(
        Movie(
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
        Movie(
            id = 1001,
            backdropPath = "https://image.tmdb.org/t/p/w500/or06FN3Dka5tukK1e9sl16pB3iy.jpg",
            originalTitle = "",
            overview = "",
            posterPath = "",
            adult = false,
            title = "Avengers: Endgame 1",
            originalLanguage = "A",
            genreIds = listOf(),
            releaseDate = "2019-04-24",
            popularity = 100.0,
            video = false,
            voteAverage = 8.255,
            voteCount = 100000,
        ),
        Movie(
            id = 1002,
            backdropPath = "https://image.tmdb.org/t/p/w500/or06FN3Dka5tukK1e9sl16pB3iy.jpg",
            originalTitle = "",
            overview = "",
            posterPath = "",
            adult = false,
            title = "Avengers: Endgame 2",
            originalLanguage = "A",
            genreIds = listOf(),
            releaseDate = "2019-04-24",
            popularity = 100.0,
            video = false,
            voteAverage = 8.255,
            voteCount = 100000,
        ),
    )

    val movieInfoList = movieList.map { it.toInfo() }

    val moviePaginated = PaginatedList(
        1, movieList, 1, 3
    )

    val movieInfoPaginated = PaginatedList(
        1, movieInfoList, 1, 3
    )

    val successMovieInfoDataResult: DataResult<PaginatedList<MovieInfo>, DataError> = DataResult.Success(movieInfoPaginated)
    val errorMovieInfoDataResult: DataResult<PaginatedList<MovieInfo>, DataError> = DataResult.Error(DataError.Network.UNKNOWN)


}
