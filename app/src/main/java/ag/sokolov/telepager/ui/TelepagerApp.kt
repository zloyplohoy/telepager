package ag.sokolov.telepager.ui

import ag.sokolov.telepager.navigation.TelepagerNavHost
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration.Short
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult.ActionPerformed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier

@Composable
fun TelepagerApp(
    appState: TelepagerAppState,
) {
    val snackbarHostState = remember { SnackbarHostState() }

    TelepagerApp(
        appState = appState,
        snackbarHostState = snackbarHostState
    )
}

@Composable
internal fun TelepagerApp(
    appState: TelepagerAppState,
    snackbarHostState: SnackbarHostState,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        modifier = modifier
    ) { padding ->
        Box(
            Modifier
                .fillMaxSize()
                .padding(padding)
                .consumeWindowInsets(padding)
        ) {
            TelepagerNavHost(
                appState = appState,
                onShowSnackbar = { message ->
                    snackbarHostState.showSnackbar(
                        message = message,
                        duration = Short
                    ) == ActionPerformed
                }
            )
        }
    }
}
