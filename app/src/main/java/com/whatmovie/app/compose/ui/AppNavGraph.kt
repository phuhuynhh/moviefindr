package com.whatmovie.app.compose.ui

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import com.whatmovie.app.compose.ui.base.BaseDestination
import com.whatmovie.app.compose.ui.screens.main.mainNavGraph
import timber.log.Timber

@Composable
fun AppNavGraph(
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        route = AppDestination.RootNavGraph.route,
        startDestination = AppDestination.MainNavGraph.destination
    ) {
        mainNavGraph(navController = navController)
    }
}

fun NavGraphBuilder.composable(
    destination: BaseDestination,
    content: @Composable AnimatedContentScope.(NavBackStackEntry) -> Unit,
) {
    composable(
        route = destination.route,
        arguments = destination.arguments,
        deepLinks = destination.deepLinks.map {
            navDeepLink {
                uriPattern = it
            }
        },
        enterTransition = {
            fadeIn(
                animationSpec = tween(
                    800, easing = FastOutSlowInEasing
                )
            )
//                    slideIntoContainer(
//                        animationSpec = tween(300, easing = EaseIn),
//                        towards = AnimatedContentTransitionScope.SlideDirection.Start
//                    )
        },
        exitTransition = {
            fadeOut(
                animationSpec = tween(
                    800, easing = LinearOutSlowInEasing
                )
            )
//            +
//                    slideOutOfContainer(
//                        animationSpec = tween(300, easing = EaseOut),
//                        towards = AnimatedContentTransitionScope.SlideDirection.End
//                    )
        },

        content = content
    )
}

/**
 * Navigate to provided [BaseDestination] with a Pair of key value String and Data [parcel]
 * Caution to use this method. This method use savedStateHandle to store the Parcelable data.
 * When previousBackstackEntry is popped out from navigation stack, savedStateHandle will return null and cannot retrieve data.
 * eg.Login -> Home, the Login screen will be popped from the back-stack on logging in successfully.
 */
fun NavHostController.navigate(destination: BaseDestination, parcel: Pair<String, Any?>? = null) {
    when (destination) {
        is BaseDestination.Up -> {
            destination.results.forEach { (key, value) ->
                previousBackStackEntry?.savedStateHandle?.set(key, value)
            }
            navigateUp()
        }

        else -> {
            Timber.d("navigate --> $destination | $parcel")
            parcel?.let { (key, value) ->
                currentBackStackEntry?.savedStateHandle?.set(key, value)
            }
            navigate(route = destination.destination)
        }
    }
}
