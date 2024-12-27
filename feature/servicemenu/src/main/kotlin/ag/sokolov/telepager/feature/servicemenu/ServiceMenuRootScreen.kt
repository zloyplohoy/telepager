package ag.sokolov.telepager.feature.servicemenu

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Message
import androidx.compose.material.icons.filled.Android
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun ServiceMenuRootScreen(
    navigateToBotServiceMenu: () -> Unit = {},
    navigateToRecipientServiceMenu: () -> Unit = {},
    navigateToMessagesServiceMenu: () -> Unit = {},
    navigateToHomeScreen: () -> Unit = {},
    modifier: Modifier = Modifier,
) {
    Column {
        ListItem(
            leadingContent = { Icon(Icons.Filled.Android, null) },
            headlineContent = { Text("Bot") },
            modifier = Modifier.clickable { navigateToBotServiceMenu() }
        )
        ListItem(
            leadingContent = { Icon(Icons.Filled.Person, null) },
            headlineContent = { Text("Recipients") },
            modifier = Modifier.clickable { navigateToRecipientServiceMenu() }
        )
        ListItem(
            leadingContent = { Icon(Icons.AutoMirrored.Filled.Message, null) },
            headlineContent = { Text("Messages") },
            modifier = Modifier.clickable { navigateToMessagesServiceMenu() }
        )
        ListItem(
            leadingContent = { Icon(Icons.Filled.Star, null) },
            headlineContent = { Text("New home screen") },
            modifier = Modifier.clickable { navigateToHomeScreen() }
        )
    }
}

@Preview
@Composable
private fun PreviewServiceMenuRootScreen() {
    ServiceMenuRootScreen()
}
