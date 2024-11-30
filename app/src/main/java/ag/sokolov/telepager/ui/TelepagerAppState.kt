package ag.sokolov.telepager.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.CoroutineScope

@Composable
fun rememberTelepagerAppState(
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    navController: NavHostController = rememberNavController(),
) =
    remember(coroutineScope, navController) {
        TelepagerAppState(
            navController = navController
        )
    }

@Stable
class TelepagerAppState(
    val navController: NavHostController,
)
