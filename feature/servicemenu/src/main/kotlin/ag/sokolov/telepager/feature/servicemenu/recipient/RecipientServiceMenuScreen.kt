package ag.sokolov.telepager.feature.servicemenu.recipient

import ag.sokolov.telepager.core.model.UserDetails
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
fun RecipientServiceMenuScreen(
    viewModel: RecipientServiceMenuViewModel = hiltViewModel(),
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    RecipientServiceMenuScreen(
        state = state,
        onTokenValueChange = viewModel::onTokenValueChange,
        onUserIdValueChange = viewModel::onUserIdValueChange,
        getUser = viewModel::getUser
    )
}

@Composable
internal fun RecipientServiceMenuScreen(
    state: UserServiceMenuState = UserServiceMenuState(),
    onTokenValueChange: (String) -> Unit = {},
    onUserIdValueChange: (String) -> Unit = {},
    getUser: () -> Unit = {},
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
        Button(
            onClick = getUser
        ) {
            Text("Get user")
        }
        TextField(
            value = state.userDetails?.username ?: "None",
            readOnly = true,
            onValueChange = {},
            label = { Text("User username") },
            modifier = Modifier.fillMaxWidth()
        )
        TextField(
            value = state.userDetails?.firstName ?: "None",
            readOnly = true,
            onValueChange = {},
            label = { Text("User first name") },
            modifier = Modifier.fillMaxWidth()
        )
        TextField(
            value = state.userDetails?.lastName ?: "None",
            readOnly = true,
            onValueChange = {},
            label = { Text("User last name") },
            modifier = Modifier.fillMaxWidth()
        )
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
private fun PreviewRecipientServiceMenuScreen() {
    RecipientServiceMenuScreen(UserServiceMenuState())
}

data class UserServiceMenuState(
    val botToken: String = "",
    val userId: String = "",
    val userDetails: UserDetails? = null,
    val error: String? = null,
)
