package ag.sokolov.telepager.feature.home.component

import ag.sokolov.telepager.core.designsystem.icon.TelepagerIcons
import ag.sokolov.telepager.core.designsystem.theme.TelepagerTheme
import ag.sokolov.telepager.core.model.Recipient
import ag.sokolov.telepager.core.model.RecipientState
import ag.sokolov.telepager.core.model.SampleRecipientState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun RecipientListItem(
    recipientState: RecipientState,
    onDeleteRecipient: () -> Unit,
    modifier: Modifier = Modifier,
) {
    ListItem(
        modifier = modifier
            .clip(RoundedCornerShape(4.dp)),
        leadingContent = {
            Icon(
                imageVector = TelepagerIcons.Person,
                contentDescription = null
            )
        },
        headlineContent = {
            Text(
                text = recipientState.recipient.getFullName(),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        supportingContent = {
            recipientState.recipient.username?.let {
                Text(
                    text = "@$it",
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        },
        trailingContent = {
            IconButton(
                onClick = onDeleteRecipient
            ) {
                Icon(
                    imageVector = TelepagerIcons.Delete,
                    contentDescription = null
                )
            }
        }
    )
}

fun Recipient.getFullName() =
    if (this.lastName == null) {
        this.firstName
    } else {
        "${this.firstName} ${this.lastName}"
    }

@Preview
@Composable
private fun PreviewRecipientListItemFirstName() {
    TelepagerTheme {
        Surface {
            RecipientListItem(
                recipientState = SampleRecipientState.NAME_ONLY,
                onDeleteRecipient = {}
            )
        }
    }
}

@Preview
@Composable
private fun PreviewRecipientListItemFullName() {
    TelepagerTheme {
        Surface {
            RecipientListItem(
                recipientState = SampleRecipientState.FULL_NAME,
                onDeleteRecipient = {}
            )
        }
    }
}

@Preview
@Composable
private fun PreviewRecipientListItemFullNameUsername() {
    TelepagerTheme {
        Surface {
            RecipientListItem(
                recipientState = SampleRecipientState.FULL_NAME_AND_USERNAME,
                onDeleteRecipient = {}
            )
        }
    }
}

@Preview(widthDp = 300)
@Composable
private fun PreviewRecipientListItemFullNameUsernameNarrow() {
    TelepagerTheme {
        Surface {
            RecipientListItem(
                recipientState = SampleRecipientState.FULL_NAME_AND_USERNAME_EXTRA_LONG,
                onDeleteRecipient = {}
            )
        }
    }
}
