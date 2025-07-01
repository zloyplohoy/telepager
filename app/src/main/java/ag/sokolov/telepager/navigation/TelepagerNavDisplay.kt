package ag.sokolov.telepager.navigation

import ag.sokolov.telepager.feature.home.navigation.BotScreenNavEntry
import ag.sokolov.telepager.feature.home.navigation.HomeScreenNavEntry
import ag.sokolov.telepager.feature.home.navigation.HomeScreenNavKey
import ag.sokolov.telepager.feature.home.navigation.PermissionsScreenNavEntry
import ag.sokolov.telepager.feature.home.navigation.RecipientsScreenNavEntry
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSavedStateNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import androidx.navigation3.ui.rememberSceneSetupNavEntryDecorator

@Composable
fun TelepagerNavDisplay(
    modifier: Modifier = Modifier
) {
    val backStack = rememberNavBackStack(HomeScreenNavKey)

    NavDisplay(
        backStack = backStack,
        entryDecorators = listOf(
            rememberSceneSetupNavEntryDecorator(),
            rememberSavedStateNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator()
        ),
        entryProvider = entryProvider {
            HomeScreenNavEntry(onNavigate = { backStack.add(it) })
            BotScreenNavEntry(onBackClick = backStack::removeLastOrNull)
            RecipientsScreenNavEntry(onBackClick = backStack::removeLastOrNull)
            PermissionsScreenNavEntry(onBackClick = backStack::removeLastOrNull)
        },
        transitionSpec = {
            slideInHorizontally(initialOffsetX = { it }) togetherWith
                    slideOutHorizontally(targetOffsetX = { -it })
        },
        popTransitionSpec = {
            slideInHorizontally(initialOffsetX = { -it }) togetherWith
                    slideOutHorizontally(targetOffsetX = { it })
        },
        predictivePopTransitionSpec = {
            slideInHorizontally(initialOffsetX = { -it }) togetherWith
                    slideOutHorizontally(targetOffsetX = { it })
        },
        modifier = modifier
    )
}