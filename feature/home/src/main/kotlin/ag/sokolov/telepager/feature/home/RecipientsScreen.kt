package ag.sokolov.telepager.feature.home

import ag.sokolov.telepager.core.designsystem.component.TelepagerScreenTitle
import ag.sokolov.telepager.core.designsystem.theme.TelepagerTheme
import ag.sokolov.telepager.core.model.Recipient
import ag.sokolov.telepager.feature.home.component.RecipientListItem
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun RecipientsScreen(
    viewModel: HomeViewModel,
    onBackClick: () -> Unit,
) {
    val state by viewModel.stateFlow.collectAsStateWithLifecycle()

    RecipientsScreen(
        state = state,
        onBackClick = onBackClick,
        onDeleteRecipient = viewModel::deleteRecipient
    )
}

@Composable
internal fun RecipientsScreen(
    state: HomeScreenState,
    onBackClick: () -> Unit,
    onDeleteRecipient: (Long) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        TelepagerScreenTitle(
            title = "Recipients",
            onBackClick = onBackClick,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        ListItem(
            modifier = Modifier.padding(horizontal = 16.dp),
            headlineContent = { Text("Add recipient") },
            leadingContent = {
                Icon(
                    imageVector = Icons.Rounded.Add,
                    contentDescription = null
                )
            }
        )
        Text(
            modifier = Modifier.padding(horizontal = 32.dp, vertical = 16.dp),
            style = MaterialTheme.typography.labelMedium,
            text = "Active recipients"
        )
        state.recipients.forEach {
            RecipientListItem(
                recipient = it,
                onDeleteRecipient = { onDeleteRecipient(it.id) },
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }
    }
}

@Preview
@Composable
private fun PreviewRecipientsScreen() {
    TelepagerTheme {
        Surface {
            RecipientsScreen(
                state = HomeScreenState(
                    recipients = listOf(
                        Recipient(
                            id = 0,
                            firstName = "Konstantin",
                            lastName = "Konstantinopolskii",
                            username = "konstantonos",
                            isBotBlocked = false
                        ),
                        Recipient(
                            id = 0,
                            firstName = "Aleksandra",
                            lastName = "Chernovorotova",
                            username = "chernovorot",
                            isBotBlocked = false
                        ),
                        Recipient(
                            id = 0,
                            firstName = "Nikolai",
                            lastName = "Dostoevskii-Krupenskii",
                            username = "not_dostoevskii",
                            isBotBlocked = false
                        )
                    )
                ),
                onBackClick = {},
                onDeleteRecipient = {}
            )
        }
    }
}
