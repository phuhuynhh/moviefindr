package com.whatmovie.app.compose.domain.models

data class MovieInfo(
    val id: Long = 0,
    val backdropPath: String = "",
    val originalTitle: String,
    val overview: String,
    val posterPath: String,
    val adult: Boolean = false,
    val title: String = "",
    val originalLanguage: String,
    val genreIds: List<Int> = listOf(),
    val releaseDate: String = "",
    val popularity: Double = 0.0,
    val video: Boolean = false,
    val voteAverage: Double = 0.0,
    val voteCount: Int = 0
)
