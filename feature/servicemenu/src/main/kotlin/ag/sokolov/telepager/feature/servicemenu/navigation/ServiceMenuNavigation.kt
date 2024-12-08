package ag.sokolov.telepager.feature.servicemenu.navigation

import ag.sokolov.telepager.feature.servicemenu.LegacyServiceMenuScreen
import ag.sokolov.telepager.feature.servicemenu.ServiceMenuRootScreen
import ag.sokolov.telepager.feature.servicemenu.ServiceMenuViewModel
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import kotlinx.serialization.Serializable

@Serializable
object ServiceMenu

@Serializable
object ServiceMenuRoot

@Serializable
object LegacyServiceMenu

fun NavGraphBuilder.serviceMenuFeature(navController: NavHostController) {
    navigation<ServiceMenu>(startDestination = ServiceMenuRoot) {
        composable<ServiceMenuRoot> {
            ServiceMenuRootScreen(
                navigateToLegacyServiceMenu = { navController.navigateToLegacyServiceMenu() }
            )
        }
        composable<LegacyServiceMenu> {
            val parentEntry = remember(it) { navController.getBackStackEntry(ServiceMenu) }
            val serviceMenuViewModel: ServiceMenuViewModel = hiltViewModel(parentEntry)
            LegacyServiceMenuScreen(viewModel = serviceMenuViewModel)
        }
    }
}

internal fun NavController.navigateToLegacyServiceMenu() =
    navigate(LegacyServiceMenu)
