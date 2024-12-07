package ag.sokolov.telepager.feature.servicemenu.navigation

import ag.sokolov.telepager.feature.servicemenu.ServiceMenuScreen
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
object ServiceMenuRoute

fun NavController.navigateToServiceMenu() =
    navigate(route = ServiceMenuRoute)

fun NavGraphBuilder.serviceMenuScreen() {
    composable<ServiceMenuRoute> {
        ServiceMenuScreen()
    }
}
