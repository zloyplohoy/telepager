package ag.sokolov.telepager.feature.bot.navigation

import ag.sokolov.telepager.feature.bot.BotScreen
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
object BotScreenRoute

fun NavController.navigateToBot(navOptions: NavOptions? = null) =
    navigate(BotScreenRoute, navOptions)

fun NavGraphBuilder.botScreen(
    onBackClick: () -> Unit,
) {
    composable<BotScreenRoute> {
        BotScreen(onBackClick = onBackClick)
    }
}
