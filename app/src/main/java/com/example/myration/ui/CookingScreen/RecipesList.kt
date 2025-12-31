package com.example.myration.ui.CookingScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.core_ui.list_modifiers.BadgeWidget
import com.example.domain.model.Recipe
import com.example.myration.R
import com.example.myration.maping.getBadgesDesc
import com.example.theme.SecondaryColor
import com.example.theme.SecondaryHalfTransparentColor
import com.example.theme.MyRationTypography

@Composable
fun RecipesList(recipeList: List<Recipe>, navigateToRecipeDetails: (recipeId: Int) -> Unit) {
    LazyColumn(modifier = Modifier.padding(top = 50.dp, start = 20.dp, end = 20.dp)) {
        itemsIndexed(recipeList.chunked(3)) { index, chunk ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                if (index % 2 == 0) {
                    chunk.take(2).forEach { recipe ->
                        Box(modifier = Modifier
                            .weight(1f)
                            .clickable { navigateToRecipeDetails(recipe.id) }) {
                            RecipeItemShort(recipe)
                            recipe.getBadgesDesc()?.let {
                                BadgeWidget(it)
                            }
                        }
                    }
                } else {
                    RecipeItemLong(chunk.first(), modified = Modifier.fillMaxWidth().clickable { navigateToRecipeDetails(chunk.first().id) })
                }
            }
        }
    }
}
@Composable
fun RecipeItemLong(recipe: Recipe, modified: Modifier) {
    Box(
        modifier = modified.padding(top = 15.dp)
        ){
        Row(
            modifier = modified
                .padding(horizontal = 10.dp, vertical = 25.dp)
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
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = recipe.type.desc,
                    style = MyRationTypography.displaySmall,
                    color = SecondaryColor
                )
                Spacer(modifier = Modifier.height(20.dp))
                AsyncImage(
                    model = recipe.thumbnail,
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
            Spacer(modifier = Modifier.width(20.dp))
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = recipe.name,
                    style = MyRationTypography.titleLarge,
                    color = SecondaryColor,
                    modifier = Modifier.width(130.dp),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(15.dp))
                Text(
                    text = recipe.kcal.toString() + " kcal",
                    style = MyRationTypography.displaySmall,
                    color = SecondaryColor
                )
            }
        }
        recipe.getBadgesDesc()?.let { BadgeWidget(it) }

    }

}

@Composable
fun RecipeItemShort(recipe: Recipe) {
    Column(
        modifier = Modifier
            .padding(top = 20.dp)
            .padding(horizontal = 10.dp)
            .fillMaxWidth()
            .height(250.dp)
            .shadow(elevation = 8.dp, shape = RoundedCornerShape(12.dp))
            .border(
                width = 1.dp,
                color = SecondaryHalfTransparentColor,
                shape = RoundedCornerShape(12.dp)
            )
            .background(color = Color.White, shape = RoundedCornerShape(12.dp))
            .padding(vertical = 8.dp, horizontal = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = recipe.type.desc,
            style = MyRationTypography.displaySmall,
            color = SecondaryColor
        )
        Spacer(modifier = Modifier.height(20.dp))
        AsyncImage(
            model = recipe.thumbnail,
            contentDescription = "recipe image",
            placeholder = painterResource(R.drawable.ic_baseline_file_download_24),
            error = painterResource(R.drawable.ic_baseline_error_outline_24),
            modifier = Modifier
                .width(70.dp)
                .height(70.dp)
                .clip(CircleShape)
                .border(2.dp, Color.Gray, CircleShape)
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = recipe.name,
            style = MyRationTypography.displayLarge,
            color = SecondaryColor,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = recipe.kcal.toString() + " kcal",
            style = MyRationTypography.displaySmall,
            color = SecondaryColor
        )
    }
}
