package br.com.hellodev.core.navigation.host

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import br.com.hellodev.core.navigation.route.BookRoutes
import br.com.hellodev.presenter.features.details.DetailsScreen
import br.com.hellodev.presenter.features.home.HomeScreen
import br.com.hellodev.presenter.features.manage.ManageScreen

@Composable
fun BookNavHost(navHostController: NavHostController) {
    NavHost(
        navController = navHostController,
        startDestination = BookRoutes.Home
    ) {
        composable<BookRoutes.Home> {
            HomeScreen(
                onBookSelect = { id ->
                    navHostController.navigate(BookRoutes.Details(id))
                },
                onCreateClick = {
                    navHostController.navigate(BookRoutes.Manage())
                }
            )
        }

        composable<BookRoutes.Details> {
            DetailsScreen(
                onEditClick = { id ->
                    navHostController.navigate(BookRoutes.Manage(id))
                },
                onBackClick = {
                    navHostController.popBackStack()
                }
            )
        }

        composable<BookRoutes.Manage> {
            ManageScreen(
                onBackPressed = {
                    navHostController.popBackStack()
                }
            )
        }
    }
}