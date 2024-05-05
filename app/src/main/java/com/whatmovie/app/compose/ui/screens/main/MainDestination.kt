package com.whatmovie.app.compose.ui.screens.main

import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.whatmovie.app.compose.ui.base.BaseDestination

const val KeyId = "id"
const val KeyModel = "model"

sealed class MainDestination {

    object Home : BaseDestination("home")

    object MovieDetail : BaseDestination("second/{$KeyId}") {

        override val arguments = listOf(
            navArgument(KeyId) { type = NavType.LongType }
        )

        fun createRoute(id: Long) = apply {
            destination = "second/$id"
        }
    }
}
