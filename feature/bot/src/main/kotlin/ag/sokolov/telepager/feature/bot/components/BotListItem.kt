package ag.sokolov.telepager.feature.bot.components

import ag.sokolov.telepager.core.designsystem.icon.TelepagerIcons
import ag.sokolov.telepager.core.designsystem.theme.TelepagerTheme
import ag.sokolov.telepager.core.model.Bot
import androidx.compose.foundation.clickable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
internal fun BotListItem(
    bot: Bot,
    onDeleteBot: () -> Unit,
) {
    ListItem(
        modifier = Modifier
            .clip(RoundedCornerShape(4.dp))
            .clickable { onDeleteBot() },
        leadingContent = {
            Icon(
                imageVector = TelepagerIcons.TelegramBot,
                contentDescription = null
            )
        },
        headlineContent = {
            Text(text = bot.name)
        },
        supportingContent = {
            Text(text = "@${bot.username}")
        },
        trailingContent = {
            IconButton(
                onClick = onDeleteBot
            ) {
                Icon(
                    imageVector = TelepagerIcons.Delete,
                    contentDescription = null
                )
            }
        }
    )
}

@Preview
@Composable
private fun PreviewBotListItem() {
    TelepagerTheme {
        Surface {
            BotListItem(
                bot = Bot(
                    isTokenValid = true,
                    name = "A beautiful bot name",
                    username = "a_beautiful_bot_name"
                ),
                onDeleteBot = {}
            )
        }
    }
}
