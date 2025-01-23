package ag.sokolov.telepager.feature.home

import ag.sokolov.telepager.core.designsystem.component.TelepagerScreenTitle
import ag.sokolov.telepager.core.designsystem.theme.TelepagerTheme
import ag.sokolov.telepager.core.model.SampleRecipientState
import ag.sokolov.telepager.feature.home.component.RecipientListItem
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Card
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
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import qrgenerator.qrkitpainter.rememberQrKitPainter

@Composable
fun RecipientsScreen(
    viewModel: HomeViewModel,
    onBackClick: () -> Unit,
) {
    val state by viewModel.stateFlow.collectAsStateWithLifecycle()
    val registrationState by viewModel.recipientRegistrationStateFlow.collectAsStateWithLifecycle()

    RecipientsScreen(
        state = state,
        registrationState = registrationState,
        onBackClick = onBackClick,
        onAddRecipient = viewModel::startRecipientRegistration,
        onDismissRecipientRegistration = viewModel::stopRecipientRegistration,
        onDeleteRecipient = viewModel::deleteRecipient
    )
}

@Composable
internal fun RecipientsScreen(
    state: HomeScreenState,
    registrationState: RegistrationState,
    onBackClick: () -> Unit,
    onAddRecipient: () -> Unit,
    onDismissRecipientRegistration: () -> Unit,
    onDeleteRecipient: (Long) -> Unit,
) {

    if (registrationState.showRegistration) {
        val registrationQrCodePainter = rememberQrKitPainter(data = registrationState.tgUrl!!)
        Dialog(
            onDismissRequest = onDismissRecipientRegistration,
        ) {
            Card {
                Image(
                    painter = registrationQrCodePainter,
                    contentDescription = null,
                    modifier = Modifier
                        .size(256.dp)
                        .padding(32.dp)
                )
            }

        }
    }
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
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .clickable { onAddRecipient() },
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
        state.recipientStateList.forEach {
            RecipientListItem(
                recipientState = it,
                onDeleteRecipient = { onDeleteRecipient(it.recipient.id) },
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
                    recipientStateList = listOf(
                        SampleRecipientState.FULL_NAME,
                        SampleRecipientState.FULL_NAME_AND_USERNAME
                    )
                ),
                registrationState = RegistrationState(),
                onBackClick = {},
                onAddRecipient = {},
                onDismissRecipientRegistration = {},
                onDeleteRecipient = {}
            )
        }
    }
}

@Preview
@Composable
private fun PreviewRecipientsScreenRegistrationStarted() {
    TelepagerTheme {
        Surface {
            RecipientsScreen(
                state = HomeScreenState(
                    recipientStateList = listOf(
                        SampleRecipientState.FULL_NAME,
                        SampleRecipientState.FULL_NAME_AND_USERNAME
                    )
                ),
                registrationState = RegistrationState(showRegistration = true, tgUrl = "tgUrl"),
                onAddRecipient = {},
                onDismissRecipientRegistration = {},
                onBackClick = {},
                onDeleteRecipient = {}
            )
        }
    }
}
