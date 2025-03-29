package com.example.myration.ui.CookingScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.myration.navigation.NavigationRoute
import com.example.myration.ui.theme.SecondaryBackgroundColor
import com.example.myration.viewModels.CookingViewModel

@Composable
fun CookingScreen(
    viewModel: CookingViewModel = hiltViewModel(),
    navController: NavHostController
) {
    val recipeList by viewModel.recipesList.collectAsState()

    Row(
        modifier = Modifier
            .fillMaxSize()
            .background(SecondaryBackgroundColor)
    ) {
        FilterWidget()
        RecipesList(recipeList) { recipeId: Int ->
            navController.navigate(
                NavigationRoute.RECIPE_DETAILS_SCREEN.withArgs(
                    recipeId
                )
            )
        }
    }
}
