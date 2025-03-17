package com.example.myration.ui.CookingScreen

import coil.compose.AsyncImage
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.unit.dp
import com.example.domain.model.Recipe
import com.example.myration.R
import com.example.myration.ui.theme.PrimaryColor
import com.example.myration.ui.theme.Typography

@Composable
fun RecipesList(recipeList: List<Recipe>) {
    LazyColumn (modifier = Modifier.padding(top=50.dp)){
        items(
            count = recipeList.size,
            itemContent = { index ->
                Row(
                    modifier = Modifier
                        .padding(vertical = 12.dp, horizontal = 24.dp)
                        .fillMaxWidth()
                        .height(200.dp)
                        .shadow(elevation = 8.dp, shape = RoundedCornerShape(12.dp))
                        .border(width = 1.dp, color = Color.Black, shape = RoundedCornerShape(12.dp))
                        .background(color = Color.White, shape = RoundedCornerShape(12.dp))
                        .padding(vertical = 8.dp, horizontal = 12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterHorizontally)
                ) {
                    Text(
                        text =recipeList[index].name,
                        style = Typography.bodyLarge,
                        color = PrimaryColor,
                        modifier = Modifier.width(130.dp)
                    )
                    Spacer(modifier = Modifier.width(20.dp))
                    AsyncImage(
                        model = recipeList[index].thumbnail,
                        contentDescription = "recipe image",
                        placeholder = painterResource(R.drawable.ic_baseline_file_download_24),
                        error = painterResource(R.drawable.ic_baseline_error_outline_24),
                        modifier = Modifier
                            .width(100.dp)
                            .height(100.dp)
                            .clip(CircleShape)
                            .border(2.dp, Color.Gray, CircleShape)
                    )
                }
            }
        )
    }
}