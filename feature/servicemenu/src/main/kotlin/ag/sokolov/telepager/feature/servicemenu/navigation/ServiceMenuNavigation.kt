package ag.sokolov.telepager.feature.servicemenu.navigation

import ag.sokolov.telepager.feature.home.navigation.homeScreen
import ag.sokolov.telepager.feature.home.navigation.navigateToHome
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
object BotServiceMenu

@Serializable
object UserServiceMenu

@Serializable
object MessageServiceMenu

fun NavGraphBuilder.serviceMenuFeature(navController: NavHostController) {
    navigation<ServiceMenu>(startDestination = ServiceMenuRoot) {
        composable<ServiceMenuRoot> {
            ServiceMenuRootScreen(
                navigateToBotServiceMenu = { navController.navigate(BotServiceMenu) },
                navigateToRecipientServiceMenu = { navController.navigate(UserServiceMenu) },
                navigateToMessagesServiceMenu = { navController.navigate(MessageServiceMenu) },
                navigateToHomeScreen = { navController.navigateToHome() }
            )
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
        homeScreen(onBackClick = { navController.popBackStack() })
    }
}
