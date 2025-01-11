package ag.sokolov.telepager.feature.home.component

import ag.sokolov.telepager.core.designsystem.icon.TelepagerIcons
import ag.sokolov.telepager.core.designsystem.theme.TelepagerTheme
import ag.sokolov.telepager.core.model.Recipient
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
    state: List<Recipient>,
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
                visible = showRecipientsMenuItemSupportingText(state),
                enter = fadeIn() + expandVertically()
            ) {
                getRecipientsMenuItemSupportingText(state)?.let {
                    Text(text = it)
                }
            }
        },
        trailingContent = {
            getRecipientsMenuItemTrailingIcon(state)?.let {
                Icon(
                    imageVector = it,
                    contentDescription = null
                )
            }
        }
    )
}

internal fun showRecipientsMenuItemSupportingText(recipientList: List<Recipient>): Boolean =
    recipientList.isNotEmpty()

internal fun getRecipientsMenuItemSupportingText(recipientList: List<Recipient>): String? =
    when {
        recipientList.isEmpty() -> null
        recipientList.any { it.isBotBlocked } -> "Some recipients have blocked the bot"
        recipientList.size == 1 -> getFirstRecipientName(recipientList)
        else -> "${getFirstRecipientName(recipientList)} and ${recipientList.size - 1} more"
    }

internal fun getFirstRecipientName(recipientList: List<Recipient>): String? =
    recipientList.firstOrNull()?.run {
        this.lastName?.let { "${this.firstName} ${this.lastName}" } ?: this.firstName
    }

internal fun getRecipientsMenuItemTrailingIcon(recipientList: List<Recipient>): ImageVector? =
    when {
        recipientList.isEmpty() -> null
        recipientList.any { it.isBotBlocked } -> TelepagerIcons.Error
        else -> TelepagerIcons.CheckCircle
    }

@Preview
@Composable
private fun PreviewRecipientsMenuItem() {
    TelepagerTheme {
        Surface {
            RecipientsMenuItem(
                state = emptyList(),
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
                state = listOf(
                    Recipient(
                        id = 0,
                        firstName = "Konstantin",
                        lastName = "Konstantinopolskii",
                        username = "",
                        isBotBlocked = false
                    )
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
                state = listOf(
                    Recipient(
                        id = 0,
                        firstName = "Konstantin",
                        lastName = "Konstantinopolskii",
                        username = "",
                        isBotBlocked = false
                    ),
                    Recipient(
                        id = 0,
                        firstName = "Konstantin",
                        lastName = "Konstantinopolskii",
                        username = "",
                        isBotBlocked = false
                    ),
                    Recipient(
                        id = 0,
                        firstName = "Konstantin",
                        lastName = "Konstantinopolskii",
                        username = "",
                        isBotBlocked = false
                    )
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
                state = listOf(
                    Recipient(
                        id = 0,
                        firstName = "Konstantin",
                        lastName = "Konstantinopolsky",
                        username = "",
                        isBotBlocked = true
                    )
                ),
                onClick = {}
            )
        }
    }
}
