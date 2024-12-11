package ag.sokolov.telepager.feature.servicemenu.navigation

import ag.sokolov.telepager.feature.servicemenu.LegacyServiceMenuScreen
import ag.sokolov.telepager.feature.servicemenu.ServiceMenuRootScreen
import ag.sokolov.telepager.feature.servicemenu.bot.BotServiceMenuScreen
import ag.sokolov.telepager.feature.servicemenu.recipient.RecipientServiceMenuScreen
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

@Serializable
object BotServiceMenu

@Serializable
object UserServiceMenu

fun NavGraphBuilder.serviceMenuFeature(navController: NavHostController) {
    navigation<ServiceMenu>(startDestination = ServiceMenuRoot) {
        composable<ServiceMenuRoot> {
            ServiceMenuRootScreen(
                navigateToLegacyServiceMenu = { navController.navigate(LegacyServiceMenu) },
                navigateToBotServiceMenu = { navController.navigate(BotServiceMenu) },
                navigateToRecipientServiceMenu = { navController.navigate(UserServiceMenu) }
            )
        }
        composable<LegacyServiceMenu> {
            LegacyServiceMenuScreen()
        }
        composable<BotServiceMenu> {
            BotServiceMenuScreen()
        }
        composable<UserServiceMenu> {
            RecipientServiceMenuScreen()
        }
    }
}
