package ag.sokolov.telepager.feature.servicemenu.recipient

import ag.sokolov.telepager.core.model.Recipient
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.text.isDigitsOnly
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun RecipientServiceMenuScreen(
    viewModel: RecipientServiceMenuViewModel = hiltViewModel(),
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    RecipientServiceMenuScreen(
        state = state,
        addRecipient = viewModel::addRecipient,
        deleteRecipient = viewModel::deleteRecipient
    )
}

@Composable
internal fun RecipientServiceMenuScreen(
    state: RecipientServiceMenuState = RecipientServiceMenuState(),
    addRecipient: (String) -> Unit = {},
    deleteRecipient: (Long) -> Unit = {},
) {
    var code by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TextField(
            value = code,
            onValueChange = {
                if (it.isDigitsOnly()) {
                    code = it
                }
            },
            label = { Text("Code") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )
        TextField(
            value = state.error ?: "",
            onValueChange = {},
            enabled = false,
            isError = true,
            label = { Text("Last Error") },
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = { addRecipient(code) }
        ) {
            Text("Add recipient")
        }
        state.recipients.forEach {
            TextField(
                value = it.firstName,
                readOnly = true,
                onValueChange = {},
                label = { Text("Name") },
                modifier = Modifier.fillMaxWidth()
            )
            TextField(
                value = it.lastName ?: "None",
                readOnly = true,
                onValueChange = {},
                label = { Text("Last name") },
                modifier = Modifier.fillMaxWidth()
            )
            TextField(
                value = it.username ?: "None",
                readOnly = true,
                onValueChange = {},
                label = { Text("Username") },
                modifier = Modifier.fillMaxWidth()
            )
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = it.isBotBlocked,
                    onCheckedChange = null
                )
                Text("Bot is blocked")
                Spacer(modifier = Modifier.weight(1f))
                Checkbox(
                    checked = it.isDeleted,
                    onCheckedChange = null
                )
                Text("Account is deleted")
            }
            Button(
                onClick = { deleteRecipient(it.id) }
            ) {
                Text("Delete recipient")
            }
        }
    }
}

@Preview
@Composable
private fun PreviewRecipientServiceMenuScreen() {
    RecipientServiceMenuScreen(RecipientServiceMenuState())
}

data class RecipientServiceMenuState(
    val recipients: List<Recipient> = emptyList(),
    val error: String? = null,
)
