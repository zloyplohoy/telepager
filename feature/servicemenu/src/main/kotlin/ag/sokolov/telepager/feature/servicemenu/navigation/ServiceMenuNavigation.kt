package ag.sokolov.telepager.feature.servicemenu.navigation

import ag.sokolov.telepager.feature.servicemenu.LegacyServiceMenuScreen
import ag.sokolov.telepager.feature.servicemenu.ServiceMenuRootScreen
import ag.sokolov.telepager.feature.servicemenu.bot.BotServiceMenuScreen
import ag.sokolov.telepager.feature.servicemenu.message.MessageServiceMenuScreen
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

@Serializable
object MessageServiceMenu

fun NavGraphBuilder.serviceMenuFeature(navController: NavHostController) {
    navigation<ServiceMenu>(startDestination = ServiceMenuRoot) {
        composable<ServiceMenuRoot> {
            ServiceMenuRootScreen(
                navigateToLegacyServiceMenu = { navController.navigate(LegacyServiceMenu) },
                navigateToBotServiceMenu = { navController.navigate(BotServiceMenu) },
                navigateToRecipientServiceMenu = { navController.navigate(UserServiceMenu) },
                navigateToMessagesServiceMenu = { navController.navigate(MessageServiceMenu) }
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
        composable<MessageServiceMenu> {
            MessageServiceMenuScreen()
        }
    }
}
