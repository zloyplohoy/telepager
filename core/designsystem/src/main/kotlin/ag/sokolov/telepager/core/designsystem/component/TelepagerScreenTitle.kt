package ag.sokolov.telepager.core.designsystem.component

import ag.sokolov.telepager.core.designsystem.icon.TelepagerIcons
import ag.sokolov.telepager.core.designsystem.theme.TelepagerTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun TelepagerScreenTitle(
    title: String,
    onBackClick: (() -> Unit)? = null,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        ) {
            onBackClick?.let {
                IconButton(
                    onClick = it
                ) {
                    Icon(
                        imageVector = TelepagerIcons.Back,
                        contentDescription = null
                    )
                }
            }
        }
        Text(
            text = title,
            style = MaterialTheme.typography.displayMedium,
            modifier = Modifier.padding(vertical = 48.dp, horizontal = 16.dp)
        )
    }
}

@Preview
@Composable
private fun PreviewTelepagerScreenTitle() {
    TelepagerTheme {
        Surface {
            TelepagerScreenTitle(
                title = "Telepager"
            )
        }
    }
}

@Preview
@Composable
private fun PreviewTelepagerScreenTitleBackButton() {
    TelepagerTheme {
        Surface {
            TelepagerScreenTitle(
                title = "Telepager",
                onBackClick = {}
            )
        }
    }
}
