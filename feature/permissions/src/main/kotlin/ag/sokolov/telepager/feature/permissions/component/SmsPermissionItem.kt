package ag.sokolov.telepager.feature.permissions.component

import ag.sokolov.telepager.core.designsystem.icon.TelepagerIcons
import androidx.compose.foundation.clickable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Sms
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
internal fun SmsPermissionItem(
    isGranted: Boolean,
    onClick: () -> Unit,
) {
    ListItem(
        modifier = Modifier
            .clip(RoundedCornerShape(4.dp))
            .clickable { onClick() },
        leadingContent = {
            Icon(
                imageVector = Icons.Rounded.Sms,
                contentDescription = null
            )
        },
        headlineContent = {
            Text(text = "Read SMS messages")
        },
        supportingContent = {
            Text(text = getSupportingText(isGranted))
        },
        trailingContent = {
            Icon(
                imageVector = getTrailingIcon(isGranted),
                contentDescription = null
            )
        }
    )
}

fun getSupportingText(isGranted: Boolean): String =
    if (isGranted) "Granted" else "Click to request permission"

internal fun getTrailingIcon(isGranted: Boolean): ImageVector =
    if (isGranted) TelepagerIcons.CheckCircle else TelepagerIcons.Error

@Preview
@Composable
private fun PreviewSmsPermissionItemGranted() {
    SmsPermissionItem(
        isGranted = true,
        onClick = {}
    )
}

@Preview
@Composable
private fun PreviewSmsPermissionItemNotGranted() {
    SmsPermissionItem(
        isGranted = false,
        onClick = {}
    )
}
