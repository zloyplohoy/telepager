package ag.sokolov.telepager.navigation

import ag.sokolov.telepager.feature.home.navigation.HomeScreenRoute
import ag.sokolov.telepager.feature.home.navigation.homeScreen
import ag.sokolov.telepager.feature.permissions.navigation.navigateToPermissions
import ag.sokolov.telepager.feature.permissions.navigation.permissionsScreen
import ag.sokolov.telepager.ui.TelepagerAppState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.navigation
import kotlinx.serialization.Serializable

@Serializable
object TelepagerNavigation

@Composable
fun TelepagerNavHost(
    appState: TelepagerAppState,
    onShowSnackbar: suspend (String) -> Boolean, // TODO: What is it?
    modifier: Modifier = Modifier,
) {
    val navController = appState.navController


    NavHost(
        navController = navController,
        startDestination = TelepagerNavigation,
        modifier = modifier
    ) {
        navigation<TelepagerNavigation>(startDestination = HomeScreenRoute) {
            homeScreen(onNavigateToPermissions = navController::navigateToPermissions)
            permissionsScreen(onBackClick = navController::popBackStack)
        }
    }
}
