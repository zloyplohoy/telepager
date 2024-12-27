package ag.sokolov.telepager.feature.home.component

import ag.sokolov.telepager.core.designsystem.icon.TelepagerIcons
import ag.sokolov.telepager.core.designsystem.theme.TelepagerTheme
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
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
internal fun PermissionsMenuItem(
    onClick: () -> Unit,
) {
    val receiveSmsPermissionState = rememberPermissionState(android.Manifest.permission.RECEIVE_SMS)
    val readSmsPermissionState = rememberPermissionState(android.Manifest.permission.READ_SMS)

    PermissionsMenuItem(
        isSmsPermissionGranted = (receiveSmsPermissionState.status.isGranted && readSmsPermissionState.status.isGranted),
        onClick = onClick
    )
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
internal fun PermissionsMenuItem(
    isSmsPermissionGranted: Boolean,
    onClick: () -> Unit,
) {
    ListItem(
        modifier = Modifier
            .clip(RoundedCornerShape(4.dp))
            .clickable { onClick() },
        leadingContent = {
            Icon(
                imageVector = TelepagerIcons.Checklist,
                contentDescription = null
            )
        },
        headlineContent = {
            Text(text = "Permissions")
        },
        supportingContent = {
            Text(text = getSupportingText(isSmsPermissionGranted))
        },
        trailingContent = {
            Icon(
                imageVector = getTrailingIcon(isSmsPermissionGranted),
                contentDescription = null
            )
        }
    )
}

fun getSupportingText(isSmsPermissionGranted: Boolean): String =
    if (isSmsPermissionGranted) {
        "All permissions granted"
    } else {
        "Cannot access SMS messages"
    }

internal fun getTrailingIcon(isSmsPermissionGranted: Boolean): ImageVector =
    if (isSmsPermissionGranted) TelepagerIcons.CheckCircle else TelepagerIcons.Error

@Preview
@Composable
private fun PreviewPermissionsMenuItemSmsAccessGranted() {
    TelepagerTheme {
        Surface {
            PermissionsMenuItem(
                isSmsPermissionGranted = true,
                onClick = {}
            )
        }
    }
}

@Preview
@Composable
private fun PreviewPermissionsMenuItemSmsAccessDenied() {
    TelepagerTheme {
        Surface {
            PermissionsMenuItem(
                isSmsPermissionGranted = false,
                onClick = {}
            )
        }
    }
}
