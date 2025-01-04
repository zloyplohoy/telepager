package ag.sokolov.telepager.navigation

import ag.sokolov.telepager.feature.bot.navigation.botScreen
import ag.sokolov.telepager.feature.bot.navigation.navigateToBot
import ag.sokolov.telepager.feature.home.navigation.HomeScreenRoute
import ag.sokolov.telepager.feature.home.navigation.homeScreen
import ag.sokolov.telepager.feature.permissions.navigation.navigateToPermissions
import ag.sokolov.telepager.feature.permissions.navigation.permissionsScreen
import ag.sokolov.telepager.ui.TelepagerAppState
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.navigation
import kotlinx.serialization.Serializable

@Serializable
object TelepagerNavigation

@Composable
fun TelepagerNavHost(
    appState: TelepagerAppState,
    onShowSnackbar: suspend (String) -> Boolean, // TODO: What is it?
) {
    val navController = appState.navController

    NavHost(
        navController = navController,
        startDestination = TelepagerNavigation,
        enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left) + fadeIn() },
        exitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Left) + fadeOut() },
        popEnterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Right) + fadeIn() },
        popExitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right) + fadeOut() }
    ) {
        navigation<TelepagerNavigation>(startDestination = HomeScreenRoute) {
            homeScreen(
                onNavigateToBot = navController::navigateToBot,
                onNavigateToPermissions = navController::navigateToPermissions)
            botScreen(onBackClick = navController::popBackStack)
            permissionsScreen(onBackClick = navController::popBackStack)
        }
    }
}
