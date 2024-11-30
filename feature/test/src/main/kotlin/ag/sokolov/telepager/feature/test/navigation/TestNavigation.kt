package ag.sokolov.telepager.feature.test.navigation

import ag.sokolov.telepager.feature.test.TestScreen
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable object TestRoute

fun NavController.navigateToTest() =
    navigate(route = TestRoute)

fun NavGraphBuilder.testScreen() {
    composable<TestRoute> {
        TestScreen()
    }
}
