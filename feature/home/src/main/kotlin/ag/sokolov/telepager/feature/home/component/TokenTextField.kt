import ag.sokolov.telepager.core.designsystem.icon.TelepagerIcons
import ag.sokolov.telepager.core.designsystem.theme.TelepagerTheme
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun TokenTextField(
    value: String,
    onValueChange: (String) -> Unit,
    onPaste: () -> Unit,
    modifier: Modifier = Modifier,
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        visualTransformation = PasswordVisualTransformation(),
        shape = RoundedCornerShape(percent = 50),
        label = {
            Text(text = "Telegram bot API token")
        },
        trailingIcon = {
            // TODO: Fix color and size
            Icon(
                imageVector = TelepagerIcons.Paste,
                contentDescription = null,
                modifier = Modifier
                    .clickable { onPaste() }
                    .padding(16.dp)
            )
        },
        modifier = modifier
    )
}

@Preview
@Composable
private fun PreviewTokenTextField() {
    TelepagerTheme {
        Surface {
            TokenTextField(
                value = "",
                onValueChange = {},
                onPaste = {}
            )
        }
    }
}
