package ag.sokolov.telepager.feature.home

import TokenTextField
import ag.sokolov.telepager.core.designsystem.component.TelepagerScreenTitle
import ag.sokolov.telepager.core.designsystem.theme.TelepagerTheme
import ag.sokolov.telepager.core.model.Bot
import ag.sokolov.telepager.feature.home.component.BotListItem
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun BotScreen(
    viewModel: HomeViewModel,
    onBackClick: () -> Unit,
) {
    val state by viewModel.stateFlow.collectAsStateWithLifecycle()

    BotScreen(
        state = state,
        onBackClick = onBackClick,
        onAddBot = viewModel::addBot,
        onDeleteBot = viewModel::deleteBot
    )
}

@Composable
internal fun BotScreen(
    state: HomeScreenState,
    onBackClick: () -> Unit,
    onAddBot: (String) -> Unit,
    onDeleteBot: () -> Unit,
) {
    val clipboardManager = LocalClipboardManager.current
    var token by remember { mutableStateOf("") }
    val telegramApiTokenRegex = Regex("""^\d{10}:[A-Za-z0-9_-]{35}$""")

    fun setToken(value: String) {
        token = value
        if (telegramApiTokenRegex.matches(token)) {
            onAddBot(token)
        }
    }

    fun pasteToken() = setToken(clipboardManager.getText()?.text ?: token)

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        TelepagerScreenTitle(
            title = "Telegram Bot",
            onBackClick = onBackClick,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            if (state.bot == null) {
                TokenTextField(
                    value = token,
                    onValueChange = ::setToken,
                    onPaste = ::pasteToken,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                )
            } else {
                BotListItem(
                    bot = state.bot,
                    onDeleteBot = onDeleteBot
                )
            }
        }
    }
}

@Preview
@Composable
private fun PreviewBotScreen() {
    TelepagerTheme {
        Surface {
            BotScreen(
                state = HomeScreenState(
                    Bot(
                        isTokenValid = true,
                        name = "A beautiful bot name",
                        username = "a_beautiful_bot"
                    )
                ),
                onBackClick = {},
                onAddBot = {},
                onDeleteBot = {}
            )
        }
    }
}

@Preview
@Composable
private fun PreviewBotScreenNoBot() {
    TelepagerTheme {
        Surface {
            BotScreen(
                state = HomeScreenState(
                    bot = null
                ),
                onBackClick = {},
                onAddBot = {},
                onDeleteBot = {}
            )
        }
    }
}
