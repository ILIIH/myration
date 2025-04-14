package com.example.myration.ui.CookingScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.core_ui.calorie_counter.CalorieCounterWidget
import com.example.core_ui.filter.FilterWidget
import com.example.myration.navigation.NavigationRoute
import com.example.theme.SecondaryBackgroundColor
import com.example.myration.viewModels.CookingViewModel

@Composable
fun CookingScreen(
    viewModel: CookingViewModel = hiltViewModel(),
    navController: NavHostController
) {
    val recipeList by viewModel.recipesList.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SecondaryBackgroundColor)
            .padding(top=90.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        FilterWidget(listOf())
        CalorieCounterWidget(modifier = Modifier
            .height(100.dp)
            .width(100.dp),
            currentCalorie = 1300f,
            maxCalorie = 4000
        )
        RecipesList(recipeList) { recipeId: Int ->
            navController.navigate(
                NavigationRoute.RECIPE_DETAILS_SCREEN.withArgs(
                    recipeId
                )
            )
        }
    }
}
