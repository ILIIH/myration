package com.example.myration.ui.CookingScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.myration.R
import com.example.myration.navigation.NavigationRoute
import com.example.myration.ui.theme.PrimaryColor
import com.example.myration.ui.theme.SecondaryBackgroundColor
import com.example.myration.ui.theme.Typography
import com.example.myration.view_models.CookingViewModel
import com.example.myration.view_models.GroceriesViewModel

@Composable
fun CookingScreen(
    viewModel: CookingViewModel = hiltViewModel(),
    navController : NavHostController
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