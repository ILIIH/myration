package com.example.myration.navigation
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.myration.ui.AddProductScreen
import com.example.myration.ui.CookingScreen
import com.example.myration.ui.GroceriesListScreen

@Composable
fun AppNavHost(navController: NavHostController) {
    NavHost(navController = navController, startDestination = NavigationRoute.ADD_PRODUCTS_TAB.route) {
        composable(NavigationRoute.ADD_PRODUCTS_TAB.route) {
            AddProductScreen()
        }
        composable(NavigationRoute.COOKING_TAB.route) {
            CookingScreen()
        }
        composable(NavigationRoute.GROCERIES_LIST_TAB.route) {
            GroceriesListScreen()
        }
    }
}