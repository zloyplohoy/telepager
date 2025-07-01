package ag.sokolov.telepager.feature.home.navigation

import ag.sokolov.telepager.feature.home.screens.bot.BotScreen
import ag.sokolov.telepager.feature.home.screens.home.HomeScreen
import ag.sokolov.telepager.feature.home.screens.permissions.PermissionsScreen
import ag.sokolov.telepager.feature.home.screens.recipients.RecipientsScreen
import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.EntryProviderBuilder
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entry
import kotlinx.serialization.Serializable
import org.koin.compose.viewmodel.koinViewModel

@Serializable
data object HomeScreenNavKey : NavKey

@Composable
fun EntryProviderBuilder<NavKey>.HomeScreenNavEntry(
    onNavigate: (NavKey) -> Unit
) = entry(HomeScreenNavKey) {
    HomeScreen(
        viewModel = koinViewModel(),
        onNavigateToBotScreen = { onNavigate(BotScreenNavKey) },
        onNavigateToRecipientsScreen = { onNavigate(RecipientsScreenNavKey) },
        onNavigateToPermissionsScreen = { onNavigate(PermissionsScreenNavKey) }
    )
}

@Serializable
data object BotScreenNavKey : NavKey

@Composable
fun EntryProviderBuilder<NavKey>.BotScreenNavEntry(
    onBackClick: () -> Unit
) = entry(BotScreenNavKey) {
    BotScreen(
        viewModel = koinViewModel(),
        onBackClick = onBackClick
    )
}

@Serializable
data object RecipientsScreenNavKey : NavKey

@Composable
fun EntryProviderBuilder<NavKey>.RecipientsScreenNavEntry(
    onBackClick: () -> Unit
) = entry(RecipientsScreenNavKey) {
    RecipientsScreen(
        onBackClick = onBackClick
    )
}

@Serializable
data object PermissionsScreenNavKey : NavKey

@Composable
fun EntryProviderBuilder<NavKey>.PermissionsScreenNavEntry(
    onBackClick: () -> Unit
) = entry(PermissionsScreenNavKey) {
    PermissionsScreen(
        onBackClick = onBackClick
    )
}