package com.whatmovie.app.compose.ui.models

import android.os.Parcelable
import com.whatmovie.app.compose.domain.models.MovieInfo
import com.whatmovie.app.compose.util.Image
import kotlinx.parcelize.Parcelize

/*
The List Item (for both trending and search results) should include
an image (poster or backdrop), movie title, year and vote average.

 */
@Parcelize
data class UiMovie(
    val id: Long = 0,
    val title: String = "",
    val year: String = "",
    val image: String = "",
    val voteAverage: Double = 0.0
) : Parcelable



fun MovieInfo.toUiModel() = UiMovie(
    id = id,
    title = title,
    year = releaseDate,
    voteAverage = voteAverage,
    image = if (posterPath.isEmpty()) "" else Image.buildURL(posterPath, Image.ImageType.POSTER)
)

object SampleUiModel {
    val model1 = UiMovie(
        id = 299534,
        title = "Avengers: Endgame",
        year = "2019-04-24",
        image = "https://image.tmdb.org/t/p/w500/or06FN3Dka5tukK1e9sl16pB3iy.jpg",
        voteAverage = 8.255
    )
}
