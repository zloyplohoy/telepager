package ag.sokolov.telepager.navigation

import ag.sokolov.telepager.feature.servicemenu.navigation.ServiceMenuRoute
import ag.sokolov.telepager.feature.servicemenu.navigation.serviceMenuScreen
import ag.sokolov.telepager.ui.TelepagerAppState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost

@Composable
fun TelepagerNavHost(
    appState: TelepagerAppState,
    onShowSnackbar: suspend (String) -> Boolean, // TODO: What is it?
    modifier: Modifier = Modifier,
) {
    val navController = appState.navController

    NavHost(
        navController = navController,
        startDestination = ServiceMenuRoute,
        modifier = modifier
    ) {
        serviceMenuScreen()
    }
}
