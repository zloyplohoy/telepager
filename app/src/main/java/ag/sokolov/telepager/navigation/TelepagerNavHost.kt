package ag.sokolov.telepager.navigation

import ag.sokolov.telepager.feature.test.navigation.TestRoute
import ag.sokolov.telepager.feature.test.navigation.testScreen
import ag.sokolov.telepager.ui.TelepagerAppState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost

@Composable
fun TelepagerNavHost(
    appState: TelepagerAppState,
    onShowSnackbar: suspend (String) -> Boolean, // TODO: What is it?
    modifier: Modifier = Modifier
) {
    val navController = appState.navController

    NavHost(
        navController = navController,
        startDestination = TestRoute,
        modifier = modifier
    ) {
        testScreen()
    }
}