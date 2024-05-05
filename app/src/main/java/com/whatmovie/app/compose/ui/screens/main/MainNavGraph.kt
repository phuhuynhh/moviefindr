package com.whatmovie.app.compose.ui.screens.main

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.navigation
import com.whatmovie.app.compose.ui.AppDestination
import com.whatmovie.app.compose.ui.composable
import com.whatmovie.app.compose.ui.navigate
import com.whatmovie.app.compose.ui.screens.main.home.HomeScreen
import com.whatmovie.app.compose.ui.screens.main.detail.MovieDetailScreen

fun NavGraphBuilder.mainNavGraph(
    navController: NavHostController,
) {
    navigation(
        route = AppDestination.MainNavGraph.route,
        startDestination = MainDestination.Home.destination
    ) {
        composable(destination = MainDestination.Home) { backStackEntry ->
            HomeScreen(
                navigator = { destination ->
                    navController.navigate(destination, destination.parcelableArgument)
                },
            )
        }

        composable(destination = MainDestination.MovieDetail) { backStackEntry ->
            MovieDetailScreen(
                navigator = { destination -> navController.navigate(destination) },
                id = backStackEntry.arguments?.getLong(KeyId) ?: 0L
            )
        }
    }
}
