package com.example.myration.ui.RecipeDetailsScreen

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.ButtonDefaults
import coil.compose.AsyncImage
import com.example.core.mvi.ResultState
import com.example.core_ui.calorie_counter.CalorieCounter
import com.example.domain.model.CalorieCounter
import com.example.domain.model.RecipeIngredient
import com.example.myration.R
import com.example.myration.mvi.state.RecipeDetailViewState
import com.example.theme.PrimaryColor
import com.example.theme.PrimaryTransparentColor
import com.example.theme.SecondaryColor
import com.example.theme.SecondaryHalfTransparentColor
import com.example.theme.MyRationTypography
import com.example.myration.viewModels.RecipeDetailsViewModel

@Composable
fun RecipeDetailsScreen(
    viewModel: RecipeDetailsViewModel = hiltViewModel()
) {
    val productUpload = viewModel.recipeDetailsState.collectAsState()
    val calorieInfo by viewModel.calorie.collectAsState()

    when (val state = productUpload.value) {
        is ResultState.Success -> {
            RecipeDetailsLoaded(state.data, calorieInfo)
        }
        is ResultState.Loading -> {
            CircularProgressIndicator()
        }
        is ResultState.Error -> {
            Text(
                text = "Error: ${state.message}",
                color = Color.Red,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}


@Composable
fun RecipeDetailsLoaded(state: RecipeDetailViewState, calorieInfo: CalorieCounter) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(PrimaryTransparentColor)
    ) {
        RecipeTopBar(state)
        BlocksDivider()
        CalorieCounter(
            currentCalorie = calorieInfo.currentCalorie,
            maxCalorie = calorieInfo.maxCalorie,
            productCalorie = state.kcal,
        )
        BlocksDivider()
        IngredientsList(state.ingredients)
        BlocksDivider()
        RecipeDescription(state.instructions)
        BlocksDivider()
        VideoRecipe(state.videoId)
    }
}

@Composable
fun BlocksDivider() {
    Divider(
        color = SecondaryHalfTransparentColor,
        thickness = 2.dp,
        modifier = Modifier.padding(vertical = 30.dp, horizontal = 16.dp)
    )
}

@Composable
fun VideoRecipe(videoId: String) {
    val context = LocalContext.current
    val intent = remember { Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=$videoId")) }

    Button(
        onClick = {
            context.startActivity(intent)
        },
        modifier = Modifier
            .padding(bottom = 120.dp)
            .fillMaxWidth()
            .height(300.dp)
            .padding(horizontal = 30.dp, vertical = 30.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = SecondaryColor,
            contentColor = Color.White
        ),
        shape = RoundedCornerShape(24.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_play_icon),
                contentDescription = "Watch video",
                modifier = Modifier.size(200.dp)
            )
        }
    }
}

@Composable
fun RecipeDescription(recipe: String) {
    Text(
        text = "Recipe",
        style = MyRationTypography.titleLarge,
        color = SecondaryColor,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp, bottom = 30.dp),
        maxLines = 4,
        overflow = TextOverflow.Ellipsis,
        textAlign = TextAlign.Center
    )

    Text(
        text = recipe,
        style = MyRationTypography.displaySmall,
        color = SecondaryColor,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        textAlign = TextAlign.Justify
    )

    Button(
        onClick = {
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 30.dp, vertical = 30.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = PrimaryColor,
            contentColor = Color.White
        ),
        shape = RoundedCornerShape(24.dp)
    ) {
        Text(text = "Cooked", color = Color.White)
    }
}

@Composable
fun IngredientsList(ingredients: List<RecipeIngredient>) {
    Text(
        text = "Ingredients",
        style = MyRationTypography.titleLarge,
        color = SecondaryColor,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp),
        maxLines = 4,
        overflow = TextOverflow.Ellipsis,
        textAlign = TextAlign.Center
    )

    LazyColumn(
        modifier = Modifier
            .padding(top = 50.dp, start = 20.dp)
            .height((ingredients.size * 14).dp),
        userScrollEnabled = false
    ) {
        items(
            count = ingredients.size,
            itemContent = { index ->
                Text(
                    text = "• " + ingredients[index].productName + " " + ingredients[index].productAmount,
                    style = MyRationTypography.displaySmall,
                    color = SecondaryColor
                )
            }
        )
    }
}

@Composable
fun RecipeTopBar(state: RecipeDetailViewState) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp)
            .height(250.dp)
            .padding(vertical = 8.dp, horizontal = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterHorizontally)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = state.type.desc,
                style = MyRationTypography.displaySmall,
                color = SecondaryColor
            )
            Spacer(modifier = Modifier.height(15.dp))
            Text(
                text = state.name,
                style = MyRationTypography.titleLarge,
                color = SecondaryColor,
                modifier = Modifier.width(130.dp),
                maxLines = 4,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(15.dp))
            Text(
                text = state.kcal.toString() + " kcal",
                style = MyRationTypography.displaySmall,
                color = SecondaryColor
            )
        }
        Spacer(modifier = Modifier.width(20.dp))
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.height(30.dp))
            AsyncImage(
                model = state.thumbnail,
                contentDescription = "recipe image",
                placeholder = painterResource(R.drawable.ic_baseline_file_download_24),
                error = painterResource(R.drawable.ic_baseline_error_outline_24),
                modifier = Modifier
                    .width(130.dp)
                    .height(130.dp)
                    .clip(CircleShape)
                    .border(2.dp, SecondaryHalfTransparentColor, CircleShape)
            )
        }
    }
}
