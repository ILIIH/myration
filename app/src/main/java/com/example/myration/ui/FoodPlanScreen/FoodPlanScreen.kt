package com.example.myration.ui.FoodPlanScreen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.ButtonDefaults
import com.example.coreUi.R
import com.example.domain.model.FoodPlan
import com.example.domain.model.getMealtimesStr
import com.example.myration.navigation.NavigationRoute
import com.example.myration.viewModels.FoodPlanViewModel
import com.example.myration.viewModels.MainViewModel
import com.example.theme.MyRationTypography
import com.example.theme.PrimaryColor
import com.example.theme.PrimaryLightColor

@Composable
fun FoodPlanScreen(
    viewModel: FoodPlanViewModel = hiltViewModel(),
    navController: NavHostController,
    mainViewModel: MainViewModel
) {
    val state = viewModel.state.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize().padding(20.dp)
    ) {
        Text(
            text = "My food plan:",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(15.dp).align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(16.dp))
        state.value.firstOrNull()?.getMealtimesStr()?.let {
            Text(
                modifier = Modifier.fillMaxWidth().padding(vertical = 18.dp),
                text = it,
                style = MyRationTypography.displayLarge,
                textAlign = TextAlign.Center
            )
        }
        LazyColumn {
            items(
                count = state.value.size,
                itemContent = { index ->
                    if (index != 0) {
                        if (state.value[index].mealNumber != state.value[index - 1].mealNumber) {
                            Text(
                                modifier = Modifier.fillMaxWidth().padding(vertical = 18.dp),
                                text = state.value[index].getMealtimesStr(),
                                style = MyRationTypography.displayLarge,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                    FoodPlanItem(
                        item = state.value[index],
                        viewModel::markFoodEaten
                    )
                }
            )
        }
        Spacer(modifier = Modifier.height(32.dp))

        Row {
            Button(
                onClick = { navController.navigate(NavigationRoute.MANAGE_FOOD_PLAN_SCREEN.route) },
                modifier = Modifier
                    .align(Alignment.Bottom)
                    .offset(y = 10.dp, x = 20.dp)
                    .padding(10.dp)
                    .shadow(elevation = 8.dp, shape = CircleShape)
                    .background(color = PrimaryColor),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = PrimaryColor,
                    contentColor = Color.White
                )
            ) {
                Image(
                    painter = painterResource(id = com.example.coreUi.R.drawable.ic_manage_food_plan),
                    contentDescription = "Manage food plan",
                    modifier = Modifier
                        .size(24.dp)
                )
            }
        }
    }
}

@Composable
fun FoodPlanItem(item: FoodPlan, markFoodEaten: (id: Int) -> Unit) {
    val isChecked = remember { mutableStateOf(false) }
    val isExpanded = remember { mutableStateOf(false) }
    val expandIcon = if (isExpanded.value) R.drawable.ic_minimaze_24_black else R.drawable.ic_expand_more_24_black

    Box(
        modifier = Modifier.fillMaxWidth()
            .padding(14.dp)
            .shadow(elevation = 8.dp, shape = RoundedCornerShape(24.dp))
            .background(color = PrimaryLightColor, shape = RoundedCornerShape(24.dp))
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = expandIcon),
                    contentDescription = "expand item",
                    modifier = Modifier.clickable { isExpanded.value = !isExpanded.value }
                )
                Text(
                    text = item.mealName,
                    style = MyRationTypography.labelLarge,
                    textAlign = TextAlign.Center,
                    textDecoration = if (isChecked.value) TextDecoration.LineThrough else TextDecoration.None
                )
                Text(
                    text = "${item.mealCalorie} kcal",
                    style = MyRationTypography.labelLarge,
                    textAlign = TextAlign.Center,
                    textDecoration = if (isChecked.value) TextDecoration.LineThrough else TextDecoration.None
                )
                Checkbox(
                    checked = isChecked.value,
                    onCheckedChange = { isNewChecked ->
                        if (isNewChecked) {
                            isChecked.value = true
                            markFoodEaten(item.id)
                        }
                    },
                    enabled = !isChecked.value
                )
            }

            AnimatedVisibility(visible = isExpanded.value) {
                Column(
                    modifier = Modifier.padding(start = 40.dp, top = 20.dp, bottom = 20.dp)
                ) {
                    for (ingredient in item.ingredients) {
                        Text(
                            text = " • ${ingredient.name}   ${ingredient.amountGrams} g.",
                            style = MyRationTypography.labelMedium,
                            textDecoration = if (isChecked.value) TextDecoration.LineThrough else TextDecoration.None
                        )
                    }
                }
            }
        }
    }
}
