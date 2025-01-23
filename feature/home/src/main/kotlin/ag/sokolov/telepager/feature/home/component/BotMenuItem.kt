package ag.sokolov.telepager.feature.home.component

import ag.sokolov.telepager.core.designsystem.icon.TelepagerIcons
import ag.sokolov.telepager.core.designsystem.theme.TelepagerTheme
import ag.sokolov.telepager.core.model.BotState
import ag.sokolov.telepager.core.model.SampleBotState
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
    botState: BotState?,
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
                visible = showBotMenuItemSupportingText(botState),
                enter = fadeIn() + expandVertically()
            ) {
                getBotMenuItemSupportingText(botState)?.let {
                    Text(text = it)
                }
            }
        },
        trailingContent = {
            getBotMenuItemTrailingIcon(botState)?.let {
                Icon(
                    imageVector = it,
                    contentDescription = null
                )
            }
        }
    )
}

internal fun showBotMenuItemSupportingText(botState: BotState?): Boolean =
    botState != null

internal fun getBotMenuItemSupportingText(botState: BotState?): String? =
    botState?.let { if (it.isTokenValid) it.bot.name else "Bot token invalid" }

internal fun getBotMenuItemTrailingIcon(botState: BotState?): ImageVector? =
    botState?.let { if (it.isTokenValid) TelepagerIcons.CheckCircle else TelepagerIcons.Error }

@Preview
@Composable
private fun PreviewBotMenuItemEmpty() {
    TelepagerTheme {
        Surface {
            BotMenuItem(
                botState = null,
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
                botState = SampleBotState.VALID,
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
                botState = SampleBotState.INVALID,
                onClick = {}
            )
        }
    }
}
