package com.whatmovie.app.compose.test

import com.whatmovie.app.compose.domain.models.MovieInfo

object MockUtil {

    val movieList = listOf(
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
        MovieInfo(
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
        MovieInfo(
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
}
