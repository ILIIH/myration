package com.example.myration.navigation
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.myration.ui.AddProductScreen.AddProductManuallyScreen.AddProductManuallyScreen
import com.example.myration.ui.AddProductScreen.AddProductScreen
import com.example.myration.ui.AddProductScreen.AddProductVoice.AddProductVoiceScreen
import com.example.myration.ui.AddProductScreen.ScanFoodScreen.ScanFoodScreen
import com.example.myration.ui.CookingScreen.CookingScreen
import com.example.myration.ui.FoodPlanScreen.FoodPlanScreen
import com.example.myration.ui.FoodPlanScreen.ManageFoodPlanScreen
import com.example.myration.ui.GroceriesDetailsScreen.GroceriesDetailsScreen
import com.example.myration.ui.GroceriesListScreen.GroceriesListScreen
import com.example.myration.ui.ProfileScreen.AddEatenProductScreen
import com.example.myration.ui.ProfileScreen.ProfileScreen
import com.example.myration.ui.RationHistory.RationHistoryScreen
import com.example.myration.ui.RecipeDetailsScreen.RecipeDetailsScreen
import com.example.myration.viewModels.MainViewModel

@Composable
fun AppNavHost(navController: NavHostController, mainViewModel: MainViewModel) {
    NavHost(navController = navController, startDestination = NavigationRoute.PROFILE_TAB.route) {
        composable(NavigationRoute.RATION_HISTORY_SCREEN.route) {
            RationHistoryScreen(mainViewModel = mainViewModel)
        }
        composable(NavigationRoute.FOOD_PLAN_SCREEN.route) {
            FoodPlanScreen(navController = navController, mainViewModel = mainViewModel)
        }
        composable(NavigationRoute.MANAGE_FOOD_PLAN_SCREEN.route) {
            ManageFoodPlanScreen(navController = navController, mainViewModel = mainViewModel)
        }
        composable(NavigationRoute.ADD_PRODUCT_MANUALLY.route) {
            AddProductManuallyScreen(mainViewModel = mainViewModel)
        }
        composable(NavigationRoute.ADD_EATEN_PRODUCT_SCREEN.route) {
            AddEatenProductScreen(navController = navController)
        }
        composable(NavigationRoute.ADD_PRODUCT_VOICE.route) {
            AddProductVoiceScreen(mainViewModel = mainViewModel)
        }
        composable(NavigationRoute.PROFILE_TAB.route) {
            ProfileScreen(navController = navController, mainViewModel = mainViewModel)
        }
        composable(NavigationRoute.SCAN_PRODUCTS_SCREEN.route) {
            ScanFoodScreen(mainViewModel = mainViewModel)
        }
        composable(NavigationRoute.COOKING_TAB.route) {
            CookingScreen(navController = navController, mainViewModel = mainViewModel)
        }
        composable(
            route = NavigationRoute.RECIPE_DETAILS_SCREEN.route,
            arguments = listOf(navArgument("recipeId") { type = NavType.IntType })
        ) { _ ->
            RecipeDetailsScreen()
        }
        composable(
            route = NavigationRoute.PRODUCT_DETAILS_SCREEN.route,
            arguments = listOf(navArgument("productId") { type = NavType.IntType })
        ) { _ ->
            GroceriesDetailsScreen(navController = navController, mainViewModel = mainViewModel)
        }
        composable(NavigationRoute.ADD_PRODUCTS_TAB.route) {
            AddProductScreen(navController = navController)
        }
        composable(NavigationRoute.GROCERIES_LIST_TAB.route) {
            GroceriesListScreen(navController = navController)
        }
    }
}
