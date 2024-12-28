package ag.sokolov.telepager.feature.servicemenu.recipient

import ag.sokolov.telepager.core.model.Recipient
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
        addRecipient = viewModel::addRecipient
    )
}

@Composable
internal fun RecipientServiceMenuScreen(
    state: RecipientServiceMenuState = RecipientServiceMenuState(),
    addRecipient: (Long) -> Unit = {},
) {
    var recipientId by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TextField(
            value = recipientId,
            onValueChange = {
                if (it.isDigitsOnly()) {
                    recipientId = it
                }
            },
            label = { Text("Recipient ID") },
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = { addRecipient(recipientId.toLong()) }
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
        }
    }
}

@Preview
@Composable
private fun PreviewRecipientServiceMenuScreen() {
    RecipientServiceMenuScreen(RecipientServiceMenuState(emptyList()))
}

data class RecipientServiceMenuState(
    val recipients: List<Recipient> = emptyList(),
    val error: String? = null,
)
