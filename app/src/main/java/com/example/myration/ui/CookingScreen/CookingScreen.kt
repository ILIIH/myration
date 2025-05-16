package com.example.myration.ui.CookingScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
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
    val filtersList by viewModel.filtersList.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SecondaryBackgroundColor)
            .padding(bottom=80.dp, top = 30.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        FilterWidget(
            filters = filtersList,
            onApplyFilter = viewModel::applyFilter,
            onRemoveFilter = viewModel::removeFilter
        )
        RecipesList(recipeList) { recipeId: Int ->
            navController.navigate(
                NavigationRoute.RECIPE_DETAILS_SCREEN.withArgsRecipieID(
                    recipeId
                )
            )
        }
    }
}
