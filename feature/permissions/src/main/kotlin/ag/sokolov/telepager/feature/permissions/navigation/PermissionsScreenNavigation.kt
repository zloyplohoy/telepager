package ag.sokolov.telepager.feature.permissions.navigation

import ag.sokolov.telepager.feature.permissions.PermissionsScreen
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
object PermissionsScreenRoute

fun NavController.navigateToPermissions(navOptions: NavOptions? = null) =
    navigate(PermissionsScreenRoute, navOptions)

fun NavGraphBuilder.permissionsScreen(
    onBackClick: () -> Unit,
) {
    composable<PermissionsScreenRoute> {
        PermissionsScreen(onBackClick = onBackClick)
    }
}
