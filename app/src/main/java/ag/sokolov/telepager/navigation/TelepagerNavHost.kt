package ag.sokolov.telepager.navigation

import ag.sokolov.telepager.feature.home.navigation.HomeNavGraphRoute
import ag.sokolov.telepager.feature.home.navigation.homeNavGraph
import ag.sokolov.telepager.ui.TelepagerAppState
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost

@Composable
fun TelepagerNavHost(
    appState: TelepagerAppState,
    onShowSnackbar: suspend (String) -> Boolean, // TODO: What is it?
) {
    val navController = appState.navController

    NavHost(
        navController = navController,
        startDestination = HomeNavGraphRoute,
        enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left) + fadeIn() },
        exitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Left) + fadeOut() },
        popEnterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Right) + fadeIn() },
        popExitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right) + fadeOut() }
    ) {
        homeNavGraph(navController = navController)
    }
}
