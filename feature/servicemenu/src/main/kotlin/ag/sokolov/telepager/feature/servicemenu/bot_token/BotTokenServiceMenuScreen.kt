package ag.sokolov.telepager.feature.servicemenu.bot_token

import ag.sokolov.telepager.core.database.entity.BotTokenEntity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
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

@Composable
fun BotTokenServiceMenuScreen(
    viewModel: BotTokenServiceMenuViewModel = hiltViewModel(),
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    BotTokenServiceMenuScreen(
        state = state,
        setBotToken = viewModel::setBotToken,
        setIsBotTokenValid = viewModel::setIsBotTokenValid,
        deleteBotToken = viewModel::deleteBotToken
    )
}

@Composable
internal fun BotTokenServiceMenuScreen(
    state: BotTokenServiceMenuState,
    setBotToken: (String, Boolean) -> Unit = { _, _ -> },
    setIsBotTokenValid: (Boolean) -> Unit = {},
    deleteBotToken: () -> Unit = {},
) {
    var tokenValue by remember { mutableStateOf("") }
    var isValid by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = "Input",
            style = MaterialTheme.typography.labelMedium
        )
        TextField(
            value = tokenValue,
            onValueChange = { tokenValue = it },
            label = { Text("Token value") },
            modifier = Modifier.fillMaxWidth()
        )
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = isValid,
                onCheckedChange = { isValid = it }
            )
            Text("Is valid")
        }
        LazyVerticalGrid(
            columns = GridCells.Fixed(2)
        ) {
            item {
                Button(
                    onClick = { setBotToken(tokenValue, isValid) }
                ) {
                    Text("Set bot token")
                }
            }
            item {
                Button(
                    onClick = { setIsBotTokenValid(isValid) }
                ) {
                    Text("Set bot token validity")
                }
            }
            item {
                Button(
                    onClick = deleteBotToken
                ) {
                    Text("Delete token")
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))
        Text(
            text = "Bot token flow",
            style = MaterialTheme.typography.labelMedium
        )
        TextField(
            enabled = false,
            value = state.botToken?.value.toString(),
            onValueChange = { },
            label = { Text("Token value") },
            modifier = Modifier.fillMaxWidth()
        )
        TextField(
            enabled = false,
            value = state.botToken?.isValid.toString(),
            onValueChange = { },
            label = { Text("Token is valid") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(32.dp))
        Text(
            text = "Bot token value flow",
            style = MaterialTheme.typography.labelMedium
        )
        TextField(
            enabled = false,
            value = state.botTokenValue.toString(),
            onValueChange = { },
            label = { Text("Token value") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(32.dp))
        Text(
            text = "Bot token validity flow",
            style = MaterialTheme.typography.labelMedium
        )
        TextField(
            enabled = false,
            value = state.isBotTokenValid.toString(),
            onValueChange = { },
            label = { Text("Token is valid") },
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview
@Composable
private fun PreviewBotTokenServiceMenuScreen() {
    BotTokenServiceMenuScreen(BotTokenServiceMenuState())
}

data class BotTokenServiceMenuState(
    // TODO: Reevaluate nulls
    val botToken: BotTokenEntity? = null,
    val botTokenValue: String? = null,
    val isBotTokenValid: Boolean? = null,
)
