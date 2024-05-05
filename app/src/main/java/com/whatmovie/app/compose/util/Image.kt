package com.whatmovie.app.compose.util

import com.whatmovie.app.compose.data.remote.responses.ImageConfiguration

object Image {
    fun buildURL(path: String, type: ImageType, preset: Preset = Preset.Thumb): String {
        return DEFAULT_IMAGE_CONFIG.secureBaseUrl.plus(getDefaultSizeByImageType(type, preset)).plus(path)
    }


    private fun getDefaultSizeByImageType(type :  ImageType, preset: Preset) : String {
        return when (type){
            ImageType.BACKDROP -> DEFAULT_IMAGE_CONFIG.backdropSizes[if (preset == Preset.Thumb) 0 else 3]
            ImageType.PROFILE -> DEFAULT_IMAGE_CONFIG.profileSizes[if (preset == Preset.Thumb) 0 else 3]
            ImageType.POSTER -> DEFAULT_IMAGE_CONFIG.posterSizes[if (preset == Preset.Thumb) 3 else 6]
            ImageType.LOGO -> DEFAULT_IMAGE_CONFIG.logoSizes[if (preset == Preset.Thumb) 3 else 6]
        }
    }

    private val DEFAULT_IMAGE_CONFIG: ImageConfiguration = ImageConfiguration(
        baseUrl = "https://image.tmdb.org/t/p/",
        secureBaseUrl = "https://image.tmdb.org/t/p/",
        backdropSizes = listOf("w300", "w780", "w1280", "original"),
        posterSizes = listOf("w92", "w154", "w185", "w342", "w500", "w780", "original"),
        logoSizes = listOf("w45", "w92", "w154", "w185", "w300", "w500", "original"),
        profileSizes = listOf("w45", "w185", "h632", "original"),
        stillSizes = listOf("w92", "w185", "w300", "original")
    )

    enum class Preset {
        Thumb,
        Origin
    }

    enum class ImageType {
        BACKDROP,
        POSTER,
        LOGO,
        PROFILE
    }
}
