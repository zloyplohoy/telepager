package ag.sokolov.telepager.feature.home.navigation

import ag.sokolov.telepager.feature.home.BotScreen
import ag.sokolov.telepager.feature.home.HomeScreen
import ag.sokolov.telepager.feature.home.HomeViewModel
import ag.sokolov.telepager.feature.home.PermissionsScreen
import androidx.hilt.navigation.compose.hiltViewModel
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

fun NavGraphBuilder.homeNavGraph(navController: NavHostController) {
    navigation<HomeNavGraphRoute>(
        startDestination = HomeScreenRoute
    ) {
        homeScreen(navController = navController)
        botScreen(navController = navController)
        permissionsScreen(navController = navController)
    }
}

fun NavController.navigateToBotScreen(navOptions: NavOptions? = null) =
    navigate(BotScreenRoute, navOptions)

fun NavController.navigateToPermissionsScreen(navOptions: NavOptions? = null) =
    navigate(PermissionsScreenRoute, navOptions)

fun NavGraphBuilder.homeScreen(
    navController: NavHostController,
) =
    composable<HomeScreenRoute> {
        val viewModel: HomeViewModel =
            hiltViewModel(navController.getBackStackEntry(HomeNavGraphRoute))

        HomeScreen(
            viewModel = viewModel,
            onNavigateToBotScreen = navController::navigateToBotScreen,
            onNavigateToPermissionsScreen = navController::navigateToPermissionsScreen
        )
    }

fun NavGraphBuilder.botScreen(
    navController: NavHostController,
) =
    composable<BotScreenRoute> {
        val viewModel: HomeViewModel =
            hiltViewModel(navController.getBackStackEntry(HomeNavGraphRoute))

        BotScreen(
            viewModel = viewModel,
            onBackClick = navController::popBackStack
        )
    }

fun NavGraphBuilder.permissionsScreen(
    navController: NavHostController,
) =
    composable<PermissionsScreenRoute> {
        PermissionsScreen(onBackClick = navController::popBackStack)
    }
