package ag.sokolov.telepager.feature.servicemenu.navigation

import ag.sokolov.telepager.feature.servicemenu.LegacyServiceMenuScreen
import ag.sokolov.telepager.feature.servicemenu.ServiceMenuRootScreen
import ag.sokolov.telepager.feature.servicemenu.bot_details.BotDetailsServiceMenuScreen
import ag.sokolov.telepager.feature.servicemenu.bot_token.BotTokenServiceMenuScreen
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
object BotTokenServiceMenu

@Serializable
object BotDetailsServiceMenu

@Serializable
object UserServiceMenu

@Serializable
object MessageServiceMenu

fun NavGraphBuilder.serviceMenuFeature(navController: NavHostController) {
    navigation<ServiceMenu>(startDestination = ServiceMenuRoot) {
        composable<ServiceMenuRoot> {
            ServiceMenuRootScreen(
                navigateToLegacyServiceMenu = { navController.navigate(LegacyServiceMenu) },
                navigateToBotTokenServiceMenu = { navController.navigate(BotTokenServiceMenu) },
                navigateToBotDetailsServiceMenu = { navController.navigate(BotDetailsServiceMenu) },
                navigateToRecipientServiceMenu = { navController.navigate(UserServiceMenu) },
                navigateToMessagesServiceMenu = { navController.navigate(MessageServiceMenu) }
            )
        }
        composable<LegacyServiceMenu> {
            LegacyServiceMenuScreen()
        }
        composable<BotTokenServiceMenu> {
            BotTokenServiceMenuScreen()
        }
        composable<BotDetailsServiceMenu> {
            BotDetailsServiceMenuScreen()
        }
        composable<UserServiceMenu> {
            RecipientServiceMenuScreen()
        }
        composable<MessageServiceMenu> {
            MessageServiceMenuScreen()
        }
    }
}
