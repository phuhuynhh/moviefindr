package com.whatmovie.app.compose.domain.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

data class MovieDetailInfo(
    val id: Long = 0,
    val adult: Boolean = false,
    val backdropPath: String = "",
    val belongsToCollection: Collection? = null,
    val budget: Long = 0L,
    val homepage: String = "",
    val imdbId: String = "",
    val originalLanguage: String = "",
    val originalTitle: String = "",
    val overview: String = "",
    val popularity: Double = 0.0,
    val posterPath: String = "",
    val releaseDate: String = "",
    val revenue: Long = 0L,
    val runtime: Int = 0,
    val status: String = "",
    val tagline: String = "",
    val title: String = "",
    val isVideo: Boolean = false,
    val voteAverage: Double = 0.0,
    val voteCount: Int = 0,
    val genres: List<Genre> = listOf(),
    val productionCompanies: List<ProductionCompany> = listOf(),
    val productionCountries: List<ProductionCountry> = listOf(),
    val spokenLanguages: List<SpokenLanguage> = listOf()
)

@JsonClass(generateAdapter = true)
data class Collection(
    val id: Int,
    val name: String,
    @Json(name = "poster_path")
    val posterPath: String?,
    @Json(name = "backdrop_path")
    val backdropPath: String?
)

@JsonClass(generateAdapter = true)
data class Genre(
    val id: Int,
    val name: String
)
@JsonClass(generateAdapter = true)
data class ProductionCompany(
    val id: Int,
    @Json(name = "logo_path")
    val logoPath: String?,
    val name: String,
    @Json(name = "origin_country")
    val originCountry: String
)
@JsonClass(generateAdapter = true)
data class ProductionCountry(
    @Json(name = "iso_3166_1")
    val iso31661: String,
    val name: String
)
@JsonClass(generateAdapter = true)
data class SpokenLanguage(
    @Json(name = "english_name")
    val englishName: String,
    @Json(name = "iso_639_1")
    val iso6391: String,
    val name: String
)