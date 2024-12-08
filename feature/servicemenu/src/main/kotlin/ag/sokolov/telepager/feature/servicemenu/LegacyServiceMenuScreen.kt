package ag.sokolov.telepager.feature.servicemenu

import ag.sokolov.telepager.core.model.BotDetails
import ag.sokolov.telepager.core.model.UserDetails
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun LegacyServiceMenuScreen(
    viewModel: ServiceMenuViewModel,
    modifier: Modifier = Modifier,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var token by remember { mutableStateOf("") }
    var userId by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }

    fun onTokenValueChange(value: String) {
        token = value
    }

    fun getBot() = viewModel.getBot(token)

    fun onUserIdValueChange(sanitizedValue: String) {
        userId = sanitizedValue
    }

    fun getUser() = viewModel.getUser(token, userId.toLong())

    fun onMessageValueChange(value: String) {
        message = value
    }

    fun sendMessage() = viewModel.sendMessage(token, userId.toLong(), message)

    LegacyServiceMenuScreen(
        uiState = uiState,
        token = token,
        userId = userId,
        message = message,
        onTokenValueChange = ::onTokenValueChange,
        onUserIdValueChange = ::onUserIdValueChange,
        onMessageValueChange = ::onMessageValueChange,
        onGetBot = ::getBot,
        onGetUser = ::getUser,
        onSendMessage = ::sendMessage,
        modifier = modifier
    )
}

@Composable
internal fun LegacyServiceMenuScreen(
    uiState: ServiceMenuUiState,
    token: String = "",
    userId: String = "",
    message: String = "",
    onTokenValueChange: (String) -> Unit = { TODO("Not implemented") },
    onUserIdValueChange: (String) -> Unit = { TODO("Not implemented") },
    onMessageValueChange: (String) -> Unit = { TODO("Not implemented") },
    onGetBot: () -> Unit = { TODO("Not implemented") },
    onGetUser: () -> Unit = { TODO("Not implemented") },
    onSendMessage: () -> Unit = { TODO("Not implemented") },
    modifier: Modifier = Modifier,
) {
    Box(
        modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.verticalScroll(rememberScrollState())
        ) {
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
            Spacer(modifier = Modifier.height(64.dp))
            TextField(
                value = userId,
                onValueChange = { value ->
                    if (value.all { it.isDigit() }) {
                        onUserIdValueChange(value)
                    }
                },
                label = { Text("User ID") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )
            Button(
                onClick = onGetUser
            ) {
                Text("Get user")
            }
            TextField(
                value = uiState.userInfo?.username ?: "None",
                readOnly = true,
                onValueChange = {},
                label = { Text("User username") },
                modifier = Modifier.fillMaxWidth()
            )
            TextField(
                value = uiState.userInfo?.firstName ?: "None",
                readOnly = true,
                onValueChange = {},
                label = { Text("User first name") },
                modifier = Modifier.fillMaxWidth()
            )
            TextField(
                value = uiState.userInfo?.lastName ?: "None",
                readOnly = true,
                onValueChange = {},
                label = { Text("User last name") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(64.dp))
            TextField(
                value = message,
                onValueChange = onMessageValueChange,
                label = { Text("Message") },
                modifier = Modifier.fillMaxWidth()
            )
            Button(
                onClick = onSendMessage
            ) {
                Text("Send message")
            }
        }
    }
}

@Preview
@Composable
private fun PreviewLegacyServiceMenuScreen() {
    LegacyServiceMenuScreen(
        uiState = ServiceMenuUiState(
            botInfo = BotDetails(
                id = 0,
                name = "Mock bot",
                username = "mockbot"
            )
        )
    )
}

data class ServiceMenuUiState(
    val text: String = "Test",
    val botInfo: BotDetails? = null,
    val userInfo: UserDetails? = null,
    val error: String? = null,
)
