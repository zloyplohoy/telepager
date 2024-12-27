package ag.sokolov.telepager.feature.home.navigation

import ag.sokolov.telepager.feature.home.HomeScreen
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
object HomeScreenRoute

fun NavController.navigateToHome(navOptions: NavOptions? = null) =
    navigate(HomeScreenRoute, navOptions)

fun NavGraphBuilder.homeScreen(
    onBackClick: () -> Unit,
) {
    composable<HomeScreenRoute> {
        HomeScreen(onBackClick = onBackClick)
    }
}
