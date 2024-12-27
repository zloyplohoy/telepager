package ag.sokolov.telepager.feature.servicemenu.bot

import ag.sokolov.telepager.core.model.Bot
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

data class BotServiceMenuState(
    val bot: Bot? = null,
)

@Composable
fun BotServiceMenuScreen(
    viewModel: BotServiceMenuViewModel = hiltViewModel(),
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    BotServiceMenuScreen(
        state = state,
        onAddBot = viewModel::addBot,
        onDeleteBot = viewModel::deleteBot
    )
}

@Composable
internal fun BotServiceMenuScreen(
    state: BotServiceMenuState,
    onAddBot: (String) -> Unit = {},
    onDeleteBot: () -> Unit = {},
) {
    var tokenValue by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = "Input",
            style = MaterialTheme.typography.labelLarge
        )
        TextField(
            value = tokenValue,
            onValueChange = { tokenValue = it },
            label = { Text("Token value") },
            modifier = Modifier.fillMaxWidth()
        )
        Row {
            Button(
                onClick = { onAddBot(tokenValue) }
            ) {
                Text("Add bot")
            }
            Button(
                onClick = onDeleteBot
            ) {
                Text("Delete bot")
            }
        }


        Spacer(modifier = Modifier.height(32.dp))
        Text(
            text = "Bot flow",
            style = MaterialTheme.typography.labelLarge
        )
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = state.bot?.isValid == true,
                onCheckedChange = null
            )
            Text("Bot is valid")
        }

        TextField(
            enabled = false,
            value = state.bot?.name.toString(),
            onValueChange = { },
            label = { Text("Name") },
            modifier = Modifier.fillMaxWidth()
        )
        TextField(
            enabled = false,
            value = state.bot?.username.toString(),
            onValueChange = { },
            label = { Text("Username") },
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview
@Composable
private fun PreviewBotServiceMenuScreen() {
    BotServiceMenuScreen(state = BotServiceMenuState())
}
