package com.example.myration.navigation
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.myration.ui.AddProductScreen
import com.example.myration.ui.CookingScreen.CookingScreen
import com.example.myration.ui.GroceriesListScreen
import com.example.myration.ui.RecipeDetailsScreen.RecipeDetailsScreen

@Composable
fun AppNavHost(navController: NavHostController) {
    NavHost(navController = navController, startDestination = NavigationRoute.GROCERIES_LIST_TAB.route) {
        composable(
            route = NavigationRoute.RECIPE_DETAILS_SCREEN.route,
            arguments = listOf(navArgument("recipeId") { type = NavType.IntType })
        ) { _ ->
            RecipeDetailsScreen()
        }

        composable(NavigationRoute.ADD_PRODUCTS_TAB.route) {
            AddProductScreen()
        }
        composable(NavigationRoute.COOKING_TAB.route) {
            CookingScreen(navController =  navController)
        }
        composable(NavigationRoute.GROCERIES_LIST_TAB.route) {
            GroceriesListScreen()
        }
    }
}