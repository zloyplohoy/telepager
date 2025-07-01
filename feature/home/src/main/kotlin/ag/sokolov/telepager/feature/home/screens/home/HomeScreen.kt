package ag.sokolov.telepager.feature.home.screens.home

import ag.sokolov.telepager.core.designsystem.component.TelepagerScreenTitle
import ag.sokolov.telepager.core.designsystem.theme.TelepagerTheme
import ag.sokolov.telepager.core.model.SampleBotState
import ag.sokolov.telepager.core.model.SampleRecipientState
import ag.sokolov.telepager.feature.home.component.BotMenuItem
import ag.sokolov.telepager.feature.home.component.PermissionsMenuItem
import ag.sokolov.telepager.feature.home.component.RecipientsMenuItem
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = koinViewModel(),
    onNavigateToBotScreen: () -> Unit,
    onNavigateToRecipientsScreen: () -> Unit,
    onNavigateToPermissionsScreen: () -> Unit,
) {
    val state by viewModel.stateFlow.collectAsStateWithLifecycle()

    HomeScreen(
        state = state,
        onNavigateToBotScreen = onNavigateToBotScreen,
        onNavigateToRecipientsScreen = onNavigateToRecipientsScreen,
        onNavigateToPermissionsScreen = onNavigateToPermissionsScreen
    )
}

@Composable
internal fun HomeScreen(
    state: HomeScreenState,
    onNavigateToBotScreen: () -> Unit,
    onNavigateToRecipientsScreen: () -> Unit,
    onNavigateToPermissionsScreen: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        TelepagerScreenTitle(
            title = "Telepager",
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    BotMenuItem(
                        botState = state.botState,
                        onClick = onNavigateToBotScreen
                    )
                    RecipientsMenuItem(
                        recipientStateList = state.recipientStateList,
                        onClick = onNavigateToRecipientsScreen
                    )
                    PermissionsMenuItem(
                        onClick = onNavigateToPermissionsScreen
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun PreviewHomeScreenNotConfigured() {
    TelepagerTheme {
        Surface {
            HomeScreen(
                state = HomeScreenState(),
                onNavigateToBotScreen = {},
                onNavigateToRecipientsScreen = {},
                onNavigateToPermissionsScreen = {}
            )
        }
    }
}

@Preview
@Composable
private fun PreviewHomeScreenConfigured() {
    TelepagerTheme {
        Surface {
            HomeScreen(
                state = HomeScreenState(
                    botState = SampleBotState.VALID,
                    recipientStateList = listOf(
                        SampleRecipientState.FULL_NAME,
                        SampleRecipientState.FULL_NAME_AND_USERNAME,
                    )
                ),
                onNavigateToBotScreen = {},
                onNavigateToRecipientsScreen = {},
                onNavigateToPermissionsScreen = {}
            )
        }
    }
}
