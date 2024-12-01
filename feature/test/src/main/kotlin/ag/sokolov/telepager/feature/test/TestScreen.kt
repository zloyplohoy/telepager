package ag.sokolov.telepager.feature.test

import ag.sokolov.telepager.core.model.BotDetails
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun TestScreen(
    viewModel: TestViewModel = hiltViewModel(),
    modifier: Modifier = Modifier,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var token by remember { mutableStateOf("") }

    fun onTokenValueChange(value: String) {
        token = value
    }

    fun getBot() = viewModel.getBot(token)

    TestScreen(
        uiState = uiState,
        token = token,
        onTokenValueChange = ::onTokenValueChange,
        onGetBot = ::getBot,
        modifier = modifier
    )
}

@Composable
internal fun TestScreen(
    uiState: TestUiState,
    token: String = "",
    onTokenValueChange: (String) -> Unit,
    onGetBot: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier.fillMaxSize()
    ) {
        Column {
            TextField(
                value = token,
                onValueChange = onTokenValueChange,
                label = { Text("Telegram bot API token") },
                modifier = Modifier.fillMaxWidth()
            )
            Button(
                onClick = onGetBot
            ) {
                Text("Get bot")
            }
            TextField(
                value = uiState.botInfo?.name ?: "None",
                readOnly = true,
                onValueChange = {},
                label = { Text("Bot name") },
                modifier = Modifier.fillMaxWidth()
            )
            TextField(
                value = uiState.botInfo?.username ?: "None",
                readOnly = true,
                onValueChange = {},
                label = { Text("Bot username") },
                modifier = Modifier.fillMaxWidth()
            )
            TextField(
                value = uiState.error ?: "None",
                readOnly = true,
                isError = true,
                onValueChange = {},
                label = { Text("Error") },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Preview
@Composable
private fun PreviewTestScreen() {
    TestScreen(
        uiState = TestUiState(
            botInfo = BotDetails(
                id = 0,
                name = "Mock bot",
                username = "mockbot"
            )
        ),
        onTokenValueChange = {},
        onGetBot = {}
    )
}

data class TestUiState(
    val text: String = "Test",
    val botInfo: BotDetails? = null,
    val error: String? = null,
)
