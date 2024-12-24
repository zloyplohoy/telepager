package ag.sokolov.telepager.feature.servicemenu.bot_details


import ag.sokolov.telepager.core.model.BotDetails
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.text.isDigitsOnly
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun BotDetailsServiceMenuScreen(
    viewModel: BotDetailsServiceMenuViewModel = hiltViewModel(),
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    BotDetailsServiceMenuScreen(
        state = state,
        setBotDetails = viewModel::setBotDetails
    )
}

@Composable
internal fun BotDetailsServiceMenuScreen(
    state: BotDetailsServiceMenuState,
    setBotDetails: (Long, String, String) -> Unit = { _, _, _ -> },
) {
    var idValue by remember { mutableStateOf("") }
    var nameValue by remember { mutableStateOf("") }
    var usernameValue by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = "Input",
            style = MaterialTheme.typography.labelMedium
        )
        TextField(
            value = idValue,
            onValueChange = { if (it.isDigitsOnly()) idValue = it },
            label = { Text("ID") },
            modifier = Modifier.fillMaxWidth()
        )
        TextField(
            value = nameValue,
            onValueChange = { nameValue = it },
            label = { Text("Name") },
            modifier = Modifier.fillMaxWidth()
        )
        TextField(
            value = usernameValue,
            onValueChange = { usernameValue = it },
            label = { Text("Username") },
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = { setBotDetails(idValue.toLong(), nameValue, usernameValue) }
        ) {
            Text("Set bot details")
        }

        Spacer(modifier = Modifier.height(32.dp))
        Text(
            text = "Bot details flow",
            style = MaterialTheme.typography.labelMedium
        )
        TextField(
            enabled = false,
            value = state.botDetails?.id.toString(),
            onValueChange = { },
            label = { Text("ID") },
            modifier = Modifier.fillMaxWidth()
        )
        TextField(
            enabled = false,
            value = state.botDetails?.name.toString(),
            onValueChange = { },
            label = { Text("Name") },
            modifier = Modifier.fillMaxWidth()
        )
        TextField(
            enabled = false,
            value = state.botDetails?.username.toString(),
            onValueChange = { },
            label = { Text("Username") },
            modifier = Modifier.fillMaxWidth()
        )
    }
}

data class BotDetailsServiceMenuState(
    val botDetails: BotDetails? = null,
)
