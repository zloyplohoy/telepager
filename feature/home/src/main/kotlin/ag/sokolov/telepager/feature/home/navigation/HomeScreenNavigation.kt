package ag.sokolov.telepager.feature.home.navigation

import ag.sokolov.telepager.feature.home.HomeScreen
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
object HomeScreenRoute

fun NavGraphBuilder.homeScreen(onNavigateToPermissions: () -> Unit) {
    composable<HomeScreenRoute> {
        HomeScreen(onNavigateToPermissions = onNavigateToPermissions)
    }
}
