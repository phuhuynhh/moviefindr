package com.whatmovie.app.compose.ui.models

import com.whatmovie.app.compose.domain.models.Collection
import com.whatmovie.app.compose.domain.models.Genre
import com.whatmovie.app.compose.domain.models.MovieDetailInfo
import com.whatmovie.app.compose.domain.models.ProductionCompany
import com.whatmovie.app.compose.domain.models.ProductionCountry
import com.whatmovie.app.compose.domain.models.SpokenLanguage
import com.whatmovie.app.compose.util.Image


data class UiMovieDetail(
    val id: Long = 0,
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
)  //: Parcelable

fun MovieDetailInfo.toUiModel() = UiMovieDetail(
    id = id,
    backdropPath = Image.buildURL(backdropPath, Image.ImageType.BACKDROP, Image.Preset.Origin),
    belongsToCollection = belongsToCollection,
    budget = budget,
    homepage = homepage,
    imdbId = imdbId,
    originalLanguage = originalLanguage,
    originalTitle = originalTitle,
    overview = overview,
    popularity = popularity,
    posterPath = Image.buildURL(posterPath, Image.ImageType.POSTER, Image.Preset.Origin),
    releaseDate = releaseDate,
    revenue = revenue,
    runtime = runtime,
    status = status,
    tagline = tagline,
    title = title,
    isVideo = isVideo,
    voteAverage = voteAverage,
    voteCount = voteCount,
    genres = genres,
    productionCompanies = productionCompanies,
    productionCountries = productionCountries,
    spokenLanguages = spokenLanguages,
)

object SampleUiMovieDetail {
    val model1 = MovieDetailInfo(
        id = 1051896,
        adult = false,
        backdropPath = "/9s9o9RT9Yj6nDuRJjnJm78WFoFl.jpg",
        belongsToCollection = null,
        budget = 0,
        homepage = "",
        imdbId = "tt22939186",
        originalLanguage = "en",
        originalTitle = "Arcadian",
        overview = """In a near future,normal life on Earth has been decimated.Paul and his two sons,Thomas and Joseph,have been living a half - life – tranquility by day and torment by night . Every night, after the sun sets, they face the unrelenting attacks of a mysterious and violent evil. One day, when Thomas doesn't return home before sundown, Paul must leave the safety of their fortified farm to find him. A nightmarish battle ensues that forces the family to execute a desperate plan to survive.""",
        popularity = 67.136,
        posterPath = "/1blZfK6KtBkCGWeo7iIPlLAzzqr.jpg",
        releaseDate = "2024-04-12",
        revenue = 859453,
        runtime = 92,
        status = "Released",
        tagline = """Family is strongerthan fear .""",
        title = "Arcadian",
        isVideo = false,
        voteAverage = 0.0,
        voteCount = 28,
        genres = listOf(
            Genre(id = 878, name = "Science Fiction"),
            Genre(id = 53, name = "Thriller"),
            Genre(id = 27, name = "Horror")
        ),
        productionCompanies = listOf(
            ProductionCompany(
                id = 76991,
                logoPath = "/pQjdvbv4y9yuIDSHDEHc9ijJKaS.png",
                name = "Highland Film Group",
                originCountry = "US"
            ),
            ProductionCompany(id = 159010, logoPath = null, name = "Redline Entertainment", originCountry = ""),
            ProductionCompany(
                id = 129815, logoPath = "/qbCmnVTQEuFqrVkbxn3i9P7Jrcw.png",
                name = "Wild Atlantic Pictures",
                originCountry = "IE"
            ),
            ProductionCompany(
                id = 125584, logoPath = "/56Lrmj9izLx7SEa0uPp0fVvssea.png",
                name = "Aperture Media Partners",
                originCountry = "US"
            ),
            ProductionCompany(
                id = 831,
                logoPath = "/vLENXiYTyITnMDrTKabUXhgTKX2.png",
                name = "Saturn Films",
                originCountry = "US"
            )
        ),
        productionCountries = listOf(
            ProductionCountry(iso31661 = "IE", name = "Ireland"),
            ProductionCountry(
                iso31661 = "US",
                name = "United States of America"
            )
        ), spokenLanguages = listOf(SpokenLanguage(englishName = "English", iso6391 = "en", name = "English"))
    )
}
