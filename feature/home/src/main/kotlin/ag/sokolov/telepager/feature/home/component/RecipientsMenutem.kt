package ag.sokolov.telepager.feature.home.component

import ag.sokolov.telepager.core.designsystem.icon.TelepagerIcons
import ag.sokolov.telepager.core.designsystem.theme.TelepagerTheme
import ag.sokolov.telepager.core.model.RecipientState
import ag.sokolov.telepager.core.model.SampleRecipientState
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
internal fun RecipientsMenuItem(
    recipientStateList: List<RecipientState>,
    onClick: () -> Unit,
) {
    ListItem(
        modifier = Modifier
            .clip(RoundedCornerShape(4.dp))
            .clickable { onClick() },
        leadingContent = {
            Icon(
                imageVector = TelepagerIcons.Group,
                contentDescription = null
            )
        },
        headlineContent = {
            Text(text = "Recipients")
        },
        supportingContent = {
            AnimatedVisibility(
                visible = recipientStateList.isNotEmpty(),
                enter = fadeIn() + expandVertically()
            ) {
                getRecipientsMenuItemSupportingText(recipientStateList)?.let {
                    Text(text = it)
                }
            }
        },
        trailingContent = {
            getRecipientsMenuItemTrailingIcon(recipientStateList)?.let {
                Icon(
                    imageVector = it,
                    contentDescription = null
                )
            }
        }
    )
}

internal fun getRecipientsMenuItemSupportingText(recipientStateList: List<RecipientState>): String? =
    when {
        recipientStateList.isEmpty() -> null
        recipientStateList.any { it.isBotBlocked } -> "Some recipients have blocked the bot"
        recipientStateList.size == 1 -> getFirstRecipientName(recipientStateList)
        else -> "${getFirstRecipientName(recipientStateList)} and ${recipientStateList.size - 1} more"
    }

internal fun getFirstRecipientName(recipientStateList: List<RecipientState>): String? =
    recipientStateList.firstOrNull()?.run {
        recipient.lastName?.let {
            "${recipient.firstName} ${recipient.lastName}"
        } ?: recipient.firstName
    }

internal fun getRecipientsMenuItemTrailingIcon(recipientStateList: List<RecipientState>): ImageVector? =
    when {
        recipientStateList.isEmpty() -> null
        recipientStateList.any { it.isBotBlocked } -> TelepagerIcons.Error
        else -> TelepagerIcons.CheckCircle
    }

@Preview
@Composable
private fun PreviewRecipientsMenuItem() {
    TelepagerTheme {
        Surface {
            RecipientsMenuItem(
                recipientStateList = emptyList(),
                onClick = {}
            )
        }
    }
}

@Preview
@Composable
private fun PreviewRecipientsMenuItemSingle() {
    TelepagerTheme {
        Surface {
            RecipientsMenuItem(
                recipientStateList = listOf(
                    SampleRecipientState.FULL_NAME
                ),
                onClick = {}
            )
        }
    }
}

@Preview
@Composable
private fun PreviewRecipientsMenuItemMultiple() {
    TelepagerTheme {
        Surface {
            RecipientsMenuItem(
                recipientStateList = listOf(
                    SampleRecipientState.FULL_NAME,
                    SampleRecipientState.FULL_NAME_AND_USERNAME,
                ),
                onClick = {}
            )
        }
    }
}

@Preview
@Composable
private fun PreviewRecipientsMenuItemError() {
    TelepagerTheme {
        Surface {
            RecipientsMenuItem(
                recipientStateList = listOf(
                    SampleRecipientState.FULL_NAME_AND_USERNAME_BOT_BLOCKED
                ),
                onClick = {}
            )
        }
    }
}
