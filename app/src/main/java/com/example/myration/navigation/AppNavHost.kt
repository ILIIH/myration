package com.example.myration.navigation
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.core.util.AudioRecorder
import com.example.myration.ui.AddProductScreen.AddProductManuallyScreen
import com.example.myration.ui.AddProductScreen.AddProductScreen
import com.example.myration.ui.AddProductScreen.AddProductVoice.AddProductVoiceScreen
import com.example.myration.ui.AddProductScreen.ScanRecipeScreen
import com.example.myration.ui.CookingScreen.CookingScreen
import com.example.myration.ui.GroceriesListScreen.GroceriesListScreen
import com.example.myration.ui.RecipeDetailsScreen.RecipeDetailsScreen

@Composable
fun AppNavHost(navController: NavHostController) {
    NavHost(navController = navController, startDestination = NavigationRoute.GROCERIES_LIST_TAB.route) {
        composable(NavigationRoute.ADD_PRODUCT_MANUALLY.route) {
            AddProductManuallyScreen()
        }
        composable(NavigationRoute.ADD_PRODUCT_VOICE.route) {
            val context = LocalContext.current
            AddProductVoiceScreen(AudioRecorder(context))
        }
        composable(NavigationRoute.SCAN_PRODUCTS_SCREEN.route) {
            ScanRecipeScreen()
        }
        composable(NavigationRoute.COOKING_TAB.route) {
            CookingScreen(navController = navController)
        }
        composable(
            route = NavigationRoute.RECIPE_DETAILS_SCREEN.route,
            arguments = listOf(navArgument("recipeId") { type = NavType.IntType })
        ) { _ ->
            RecipeDetailsScreen()
        }
        composable(NavigationRoute.ADD_PRODUCTS_TAB.route) {
            AddProductScreen(navController = navController)
        }
        composable(NavigationRoute.GROCERIES_LIST_TAB.route) {
            GroceriesListScreen()
        }
    }
}
