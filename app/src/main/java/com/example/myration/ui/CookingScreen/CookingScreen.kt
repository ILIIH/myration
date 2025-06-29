package com.example.myration.ui.CookingScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.core.mvi.ResultState
import com.example.core_ui.custom_windows.ErrorMessage
import com.example.core_ui.filter.FilterWidget
import com.example.myration.mvi.effects.CookingEffect
import com.example.myration.mvi.effects.ProductDetailsEffect
import com.example.myration.mvi.state.CookingViewState
import com.example.myration.navigation.NavigationRoute
import com.example.theme.SecondaryBackgroundColor
import com.example.myration.viewModels.CookingViewModel
@Composable
fun CookingScreen(
    viewModel: CookingViewModel = hiltViewModel(),
    navController: NavHostController
) {
    val screenState = viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is CookingEffect.NavigateToRecipeDetails -> {
                    navController.navigate(
                        NavigationRoute.RECIPE_DETAILS_SCREEN.withArgsRecipieID(
                            effect.recipeId
                        )
                    )
                }

            }
        }
    }

    when (val state = screenState.value) {
        is ResultState.Success -> loadedCookingScreen(
            screenState = state.data,
            applyFilter = viewModel::applyFilter,
            removeFilter = viewModel::removeFilter,
            navigateToRecipe = viewModel::navigateToRecipe
        )
        is ResultState.Loading -> CircularProgressIndicator()
        is ResultState.Error -> ErrorMessage(
            message = state.message,
            onDismiss = {viewModel.loadData()}
        )
    }

}

@Composable
fun loadedCookingScreen(
    screenState: CookingViewState,
    applyFilter: (id: Int) -> Unit,
    removeFilter: (id: Int) -> Unit,
    navigateToRecipe: (id: Int) -> Unit
    ) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SecondaryBackgroundColor)
            .padding(bottom=80.dp, top = 30.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        FilterWidget(
            filters = screenState.filtersList,
            onApplyFilter = applyFilter,
            onRemoveFilter = removeFilter
        )
        RecipesList(screenState.recipesList,navigateToRecipe)
    }
}