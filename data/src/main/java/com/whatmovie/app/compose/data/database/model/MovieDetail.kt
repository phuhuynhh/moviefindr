package com.whatmovie.app.compose.data.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.whatmovie.app.compose.domain.models.Collection
import com.whatmovie.app.compose.domain.models.Genre
import com.whatmovie.app.compose.domain.models.MovieDetailInfo
import com.whatmovie.app.compose.domain.models.ProductionCompany
import com.whatmovie.app.compose.domain.models.ProductionCountry
import com.whatmovie.app.compose.domain.models.SpokenLanguage

@Entity(tableName = "movie_detail")
@JsonClass(generateAdapter = true)
data class MovieDetail(
    @PrimaryKey
    val id: Long,
    val adult: Boolean,
    @Json(name = "backdrop_path")
    val backdropPath: String?,
    @Json(name = "belongs_to_collection")
    val belongsToCollection: Collection?, // Change type accordingly if there's a defined structure for collection
    val budget: Long,
    val homepage: String?,
    @Json(name = "imdb_id")
    val imdbId: String?,
    @Json(name = "original_language")
    val originalLanguage: String,
    @Json(name = "original_title")
    val originalTitle: String,
    val overview: String,
    val popularity: Double,
    @Json(name = "poster_path")
    val posterPath: String?,
    @Json(name = "release_date")
    val releaseDate: String,
    val revenue: Long,
    val runtime: Int,
    val status: String,
    val tagline: String?,
    val title: String,
    @Json(name = "video")
    val isVideo: Boolean,
    @Json(name = "vote_average")
    val voteAverage: Double,
    @Json(name = "vote_count")
    val voteCount: Int,
    val genres: List<Genre>,
    @Json(name = "production_companies")
    val productionCompanies: List<ProductionCompany>,
    @Json(name = "production_countries")
    val productionCountries: List<ProductionCountry>,
    @Json(name = "spoken_languages")
    val spokenLanguages: List<SpokenLanguage>
)

fun MovieDetail.toInfo(): MovieDetailInfo =
    MovieDetailInfo(
        id = id,
        adult = adult,
        backdropPath = backdropPath ?: "",
        belongsToCollection = belongsToCollection,
        budget = budget,
        homepage = homepage ?: "",
        imdbId = imdbId ?: "",
        originalLanguage = originalLanguage,
        originalTitle = originalTitle,
        overview = overview,
        popularity = popularity,
        posterPath = posterPath ?: "",
        releaseDate = releaseDate,
        revenue = revenue,
        runtime = runtime,
        status = status,
        tagline = tagline ?: "",
        title = title,
        isVideo = isVideo,
        voteAverage = voteAverage,
        voteCount = voteCount,
        genres = genres,
        productionCompanies = productionCompanies,
        productionCountries = productionCountries,
        spokenLanguages = spokenLanguages,
    )
