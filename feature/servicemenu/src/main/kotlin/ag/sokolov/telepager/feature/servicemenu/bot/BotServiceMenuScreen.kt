package ag.sokolov.telepager.feature.servicemenu.bot

import ag.sokolov.telepager.core.model.BotDetails
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun BotServiceMenuScreen(
    viewModel: BotServiceMenuViewModel = hiltViewModel(),
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    BotServiceMenuScreen(
        state = state,
        getBot = viewModel::getBot,
        onTokenValueChange = viewModel::onTokenValueChange
    )
}

@Composable
internal fun BotServiceMenuScreen(
    state: BotServiceMenuState = BotServiceMenuState(),
    getBot: () -> Unit = {},
    onTokenValueChange: (String) -> Unit = {},
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TextField(
            value = state.botToken,
            onValueChange = { onTokenValueChange(it) },
            label = { Text("Telegram bot API token") },
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = { getBot() }
        ) {
            Text("Get bot")
        }
        TextField(
            value = state.botDetails?.name ?: "",
            readOnly = true,
            onValueChange = { },
            label = { Text("Bot name") },
            modifier = Modifier.fillMaxWidth()
        )
        TextField(
            value = state.botDetails?.username ?: "",
            readOnly = true,
            onValueChange = { },
            label = { Text("Bot username") },
            modifier = Modifier.fillMaxWidth()
        )
        TextField(
            value = state.error ?: "No error",
            readOnly = true,
            isError = true,
            onValueChange = { },
            label = { Text("Error") },
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview
@Composable
private fun PreviewBotServiceMenuScreen() {
    BotServiceMenuScreen(BotServiceMenuState())
}

data class BotServiceMenuState(
    val botToken: String = "",
    val botDetails: BotDetails? = null,
    val error: String? = null,
)
