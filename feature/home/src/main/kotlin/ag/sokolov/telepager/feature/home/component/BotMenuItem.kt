package ag.sokolov.telepager.feature.home.component

import ag.sokolov.telepager.core.designsystem.icon.TelepagerIcons
import ag.sokolov.telepager.core.designsystem.theme.TelepagerTheme
import ag.sokolov.telepager.core.model.Bot
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.clickable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
internal fun BotMenuItem(
    state: Bot?,
    onClick: () -> Unit,
) {
    ListItem(
        modifier = Modifier
            .clip(RoundedCornerShape(4.dp))
            .clickable { onClick() },
        leadingContent = {
            Icon(
                imageVector = TelepagerIcons.TelegramBot,
                contentDescription = null
            )
        },
        headlineContent = {
            Text(text = "Telegram Bot")
        },
        supportingContent = {
            AnimatedVisibility(
                visible = showBotMenuItemSupportingText(state),
                enter = fadeIn() + expandVertically()
            ) {
                getBotMenuItemSupportingText(state)?.let {
                    Text(text = it)
                }
            }
        },
        trailingContent = {
            getBotMenuItemTrailingIcon(state)?.let {
                Icon(
                    imageVector = it,
                    contentDescription = null
                )
            }
        }
    )
}

internal fun showBotMenuItemSupportingText(bot: Bot?): Boolean =
    bot != null

internal fun getBotMenuItemSupportingText(bot: Bot?): String? =
    bot?.let { if (it.isTokenValid) bot.name else "Bot token invalid" }

internal fun getBotMenuItemTrailingIcon(bot: Bot?): ImageVector? =
    bot?.let { if (it.isTokenValid) TelepagerIcons.CheckCircle else TelepagerIcons.Error }

@Preview
@Composable
private fun PreviewBotMenuItemEmpty() {
    TelepagerTheme {
        Surface {
            BotMenuItem(
                state = null,
                onClick = {}
            )
        }
    }
}

@Preview
@Composable
private fun PreviewBotMenuItemValid() {
    TelepagerTheme {
        Surface {
            BotMenuItem(
                state = Bot(
                    isTokenValid = true,
                    name = "A beautiful bot name",
                    username = ""
                ),
                onClick = {}
            )
        }
    }
}

@Preview
@Composable
private fun PreviewBotMenuItemInvalid() {
    TelepagerTheme {
        Surface {
            BotMenuItem(
                state = Bot(
                    isTokenValid = false,
                    name = "A beautiful bot name",
                    username = ""
                ),
                onClick = {}
            )
        }
    }
}
