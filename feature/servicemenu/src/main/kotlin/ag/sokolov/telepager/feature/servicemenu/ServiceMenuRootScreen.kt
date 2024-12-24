package ag.sokolov.telepager.feature.servicemenu

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.automirrored.filled.Message
import androidx.compose.material.icons.filled.AutoDelete
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Token
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun ServiceMenuRootScreen(
    navigateToBotTokenServiceMenu: () -> Unit = {},
    navigateToBotDetailsServiceMenu: () -> Unit = {},
    navigateToRecipientServiceMenu: () -> Unit = {},
    navigateToMessagesServiceMenu: () -> Unit = {},
    navigateToLegacyServiceMenu: () -> Unit = {},
    modifier: Modifier = Modifier,
) {
    Column {
        ListItem(
            leadingContent = { Icon(Icons.Filled.Token, null) },
            headlineContent = { Text("Bot token") },
            modifier = Modifier.clickable { navigateToBotTokenServiceMenu() }
        )
        ListItem(
            leadingContent = { Icon(Icons.AutoMirrored.Filled.List, null) },
            headlineContent = { Text("Bot details") },
            modifier = Modifier.clickable { navigateToBotDetailsServiceMenu() }
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
            leadingContent = { Icon(Icons.Filled.AutoDelete, null) },
            headlineContent = { Text("Legacy") },
            modifier = Modifier.clickable { navigateToLegacyServiceMenu() }
        )
    }
}

@Preview
@Composable
private fun PreviewServiceMenuRootScreen() {
    ServiceMenuRootScreen()
}
