package com.example.myration.ui.RecipeDetailsScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.core.ResultState
import com.example.myration.R
import com.example.myration.ui.theme.SecondaryColor
import com.example.myration.ui.theme.SecondaryHalfTransparentColor
import com.example.myration.ui.theme.SecondaryTransparentColor
import com.example.myration.ui.theme.Typography
import com.example.myration.view_models.RecipeDetailsViewModel

@Composable
fun RecipeDetailsScreen(
    viewModel: RecipeDetailsViewModel = hiltViewModel()
) {
    val recipe = viewModel.recipeDetailsState.collectAsState()

    when (val state = recipe.value) {
        is ResultState.Success -> {
            RecipeDetailsLoaded(state.data)
        }
        is ResultState.Loading -> {
            CircularProgressIndicator()
        }
        is ResultState.Error -> {
            Text(text = "Error: ${state.message}")
        }
    }


}

@Composable
fun RecipeDetailsLoaded(state: RecipeDetailViewState) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(SecondaryTransparentColor)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp, vertical = 30.dp)
                .height(200.dp)
                .shadow(elevation = 8.dp, shape = RoundedCornerShape(12.dp))
                .border(
                    width = 1.dp,
                    color = SecondaryHalfTransparentColor,
                    shape = RoundedCornerShape(12.dp)
                )
                .background(color = Color.White, shape = RoundedCornerShape(12.dp))
                .padding(vertical = 8.dp, horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterHorizontally)
        ) {
            Column (
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = state.name,
                    style = Typography.titleLarge,
                    color = SecondaryColor,
                    modifier = Modifier.width(130.dp),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(15.dp))
                Text(
                    text = state.kcal.toString() + " kcal",
                    style = Typography.displaySmall,
                    color = SecondaryColor,
                )
            }
            Spacer(modifier = Modifier.width(20.dp))
            Column (
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ){
                Text(
                    text = state.type.desc,
                    style = Typography.displaySmall,
                    color = SecondaryColor,
                )
                Spacer(modifier = Modifier.height(20.dp))
                AsyncImage(
                    model = state.thumbnail,
                    contentDescription = "recipe image",
                    placeholder = painterResource(R.drawable.ic_baseline_file_download_24),
                    error = painterResource(R.drawable.ic_baseline_error_outline_24),
                    modifier = Modifier
                        .width(120.dp)
                        .height(120.dp)
                        .clip(CircleShape)
                        .border(2.dp, SecondaryHalfTransparentColor, CircleShape)
                )
            }
        }
    }
}