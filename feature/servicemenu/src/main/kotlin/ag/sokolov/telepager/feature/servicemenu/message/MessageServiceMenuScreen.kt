package ag.sokolov.telepager.feature.servicemenu.message

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun MessageServiceMenuScreen(
    viewModel: MessageServiceMenuViewModel = hiltViewModel(),
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    MessageServiceMenuScreen(
        state = state,
        onTokenValueChange = viewModel::onTokenValueChange,
        onUserIdValueChange = viewModel::onUserIdValueChange,
        onMessageChange = viewModel::onMessageChange,
        sendMessage = viewModel::sendMessage
    )
}

@Composable
internal fun MessageServiceMenuScreen(
    state: MessageServiceMenuState = MessageServiceMenuState(),
    onTokenValueChange: (String) -> Unit = {},
    onUserIdValueChange: (String) -> Unit = {},
    onMessageChange: (String) -> Unit = {},
    sendMessage: () -> Unit = {},
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TextField(
            value = state.botToken,
            onValueChange = onTokenValueChange,
            label = { Text("Telegram bot API token") },
            modifier = Modifier.fillMaxWidth()
        )
        TextField(
            value = state.userId.toString(),
            onValueChange = onUserIdValueChange,
            label = { Text("User ID") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )
        TextField(
            value = state.message,
            onValueChange = onMessageChange,
            label = { Text("Message") },
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = sendMessage
        ) {
            Text("Get user")
        }
        TextField(
            value = state.error ?: "None",
            readOnly = true,
            isError = true,
            onValueChange = {},
            label = { Text("Error") },
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview
@Composable
private fun PreviewMessageServiceMenuScreen() {
    MessageServiceMenuScreen(MessageServiceMenuState())
}

data class MessageServiceMenuState(
    val botToken: String = "",
    val userId: String = "",
    val message: String = "",
    val error: String? = null,
)
