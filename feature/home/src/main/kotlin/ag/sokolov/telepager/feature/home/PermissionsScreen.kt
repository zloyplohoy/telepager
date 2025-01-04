package ag.sokolov.telepager.feature.home

import ag.sokolov.telepager.core.designsystem.component.TelepagerScreenTitle
import ag.sokolov.telepager.core.designsystem.theme.TelepagerTheme
import ag.sokolov.telepager.feature.home.component.SmsPermissionItem
import android.Manifest
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberMultiplePermissionsState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun PermissionsScreen(
    onBackClick: () -> Unit,
) {
    val smsPermissionState = rememberMultiplePermissionsState(
        listOf(
            Manifest.permission.RECEIVE_SMS,
            Manifest.permission.READ_SMS
        )
    )

    PermissionsScreen(
        onBackClick = onBackClick,
        isSmsPermissionGranted = (smsPermissionState.permissions.all { it.status.isGranted }),
        onRequestSmsPermissionClick = smsPermissionState::launchMultiplePermissionRequest
    )
}

@Composable
internal fun PermissionsScreen(
    onBackClick: () -> Unit,
    isSmsPermissionGranted: Boolean,
    onRequestSmsPermissionClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        TelepagerScreenTitle(
            title = "Permissions",
            onBackClick = onBackClick,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    SmsPermissionItem(
                        isGranted = isSmsPermissionGranted,
                        onClick = onRequestSmsPermissionClick
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun PreviewPermissionsScreenGranted() {
    TelepagerTheme {
        Surface {
            PermissionsScreen(
                onBackClick = {},
                isSmsPermissionGranted = true,
                onRequestSmsPermissionClick = {}
            )
        }
    }
}

@Preview
@Composable
private fun PreviewPermissionsScreenNotGranted() {
    TelepagerTheme {
        Surface {
            PermissionsScreen(
                onBackClick = {},
                isSmsPermissionGranted = false,
                onRequestSmsPermissionClick = {}
            )
        }
    }
}
