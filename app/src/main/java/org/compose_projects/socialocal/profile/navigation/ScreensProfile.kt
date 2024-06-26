package org.compose_projects.socialocal.profile.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import org.compose_projects.socialocal.auth.presentation.LoginScreen
import org.compose_projects.socialocal.profile.presentation.ProfileScreen
import org.compose_projects.socialocal.profile.domain.models.AccountEntity
import org.compose_projects.socialocal.common.models.ComposableStore

sealed class ScreensProfile(
    val title: String,
    val index: Int,
    val route: String,
    val screenComposable: @Composable (
        navHostControlller: NavHostController,
        composableStore: ComposableStore
    ) -> Unit
) {
    data object Profile : ScreensProfile(
        title = "Perfil",
        index = 0,
        route = "/profile",
        screenComposable = { navController, composableStore ->
            ProfileScreen(
                navController = navController,
                composableStore = composableStore
            )
        }
    )

    data object Login : ScreensProfile(
        title = "Login",
        index = 1,
        route = "/profile/login",
        screenComposable = {  navController, composableStore ->
            LoginScreen(
                paddingValues = composableStore.paddingValues
            ){
                //añadimos la nueva cuenta al dispositivo
                composableStore.accountViewModel.updateAccount(
                    AccountEntity(user = it, id = 1),
                )

                //volvemos a la vista profile
                navController.navigate("/profile",
                    builder = {
                        // Limpiar el backstack antes de navegar a la pantalla Home
                        popUpTo(navController.graph.startDestinationId) {
                            inclusive = true
                        }
                    }
                )

            }
        }
    )
}
