package com.whatmovie.app.compose.data.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.whatmovie.app.compose.domain.models.MovieInfo

@Entity(tableName = "movies")
data class Movie(
    @PrimaryKey val id: Long,
    @Json(name = "backdrop_path")
    val backdropPath: String?,
    @Json(name = "original_title")
    val originalTitle: String,
    val overview: String,
    @Json(name = "poster_path")
    val posterPath: String?,
    val adult: Boolean,
    val title: String,
    @Json(name = "original_language")
    val originalLanguage: String,
    @Json(name = "genre_ids")
    val genreIds: List<Int>,
    @Json(name = "release_date")
    val releaseDate: String,
    val popularity: Double,
    val video: Boolean,
    @Json(name = "vote_average")
    val voteAverage: Double,
    @Json(name = "vote_count")
    val voteCount: Int
)

fun Movie.toInfo(): MovieInfo =
    MovieInfo(
        id = id,
        backdropPath = backdropPath ?: "",
        originalTitle = originalTitle,
        overview = overview,
        posterPath = posterPath ?: "",
        adult = adult,
        title = title,
        originalLanguage = originalLanguage,
        genreIds = genreIds,
        releaseDate = releaseDate,
        popularity = popularity,
        video = video,
        voteAverage = voteAverage,
        voteCount = voteCount,
    )

