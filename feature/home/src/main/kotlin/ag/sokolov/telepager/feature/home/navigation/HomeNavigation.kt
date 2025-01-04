package ag.sokolov.telepager.feature.home.navigation

import ag.sokolov.telepager.feature.home.BotScreen
import ag.sokolov.telepager.feature.home.HomeScreen
import ag.sokolov.telepager.feature.home.PermissionsScreen
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import kotlinx.serialization.Serializable

@Serializable
object HomeNavGraphRoute

@Serializable
object HomeScreenRoute

@Serializable
object BotScreenRoute

@Serializable
object PermissionsScreenRoute

fun NavGraphBuilder.homeNavGraph(navController: NavHostController) =
    navigation<HomeNavGraphRoute>(
        startDestination = HomeScreenRoute
    ) {
        homeScreen(
            onNavigateToBotScreen = navController::navigateToBotScreen,
            onNavigateToPermissionsScreen = navController::navigateToPermissionsScreen
        )
        botScreen(onBackClick = navController::popBackStack)
        permissionsScreen(onBackClick = navController::popBackStack)
    }

fun NavController.navigateToBotScreen(navOptions: NavOptions? = null) =
    navigate(BotScreenRoute, navOptions)

fun NavController.navigateToPermissionsScreen(navOptions: NavOptions? = null) =
    navigate(PermissionsScreenRoute, navOptions)

fun NavGraphBuilder.homeScreen(
    onNavigateToBotScreen: () -> Unit,
    onNavigateToPermissionsScreen: () -> Unit,
) =
    composable<HomeScreenRoute> {
        HomeScreen(
            onNavigateToBotScreen = onNavigateToBotScreen,
            onNavigateToPermissionsScreen = onNavigateToPermissionsScreen
        )
    }

fun NavGraphBuilder.botScreen(
    onBackClick: () -> Unit,
) =
    composable<BotScreenRoute> {
        BotScreen(onBackClick = onBackClick)
    }

fun NavGraphBuilder.permissionsScreen(
    onBackClick: () -> Unit,
) =
    composable<PermissionsScreenRoute> {
        PermissionsScreen(onBackClick = onBackClick)
    }
