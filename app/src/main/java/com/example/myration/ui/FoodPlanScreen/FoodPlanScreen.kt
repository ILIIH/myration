package com.example.myration.ui.FoodPlanScreen

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableIntStateOf
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
import kotlin.math.absoluteValue
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
@SuppressLint("FrequentlyChangingValue")
@Composable
fun FoodPlanScreen(
    viewModel: FoodPlanViewModel = hiltViewModel(),
    navController: NavHostController,
    mainViewModel: MainViewModel
) {
    val state = viewModel.foodList.collectAsState()
    val date = viewModel.date.collectAsState()

    val isHeaderVisible = remember { mutableStateOf(true) }
    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                if (available.y < -1) {
                    isHeaderVisible.value = false
                }
                else if (available.y > 1) {
                    isHeaderVisible.value = true
                }
                return Offset.Zero
            }
        }
    }

    Column (
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(nestedScrollConnection)
    ){
        AnimatedVisibility(
            visible = isHeaderVisible.value,
            enter = expandVertically() + fadeIn(),
            exit = shrinkVertically() + fadeOut()
        ) {
            Column(
                modifier = Modifier.padding(30.dp)
            ) {
                Header(
                    isListNotEmpty = state.value.isNotEmpty(),
                    navigateToManageFoodPlan = { navController.navigate(NavigationRoute.MANAGE_FOOD_PLAN_SCREEN.route) }
                )
                DateBar(date.value, viewModel::changeDate)
            }
        }
        FoodPlanList(
            state = state.value,
            markFoodEaten = viewModel::markFoodEaten,
            navigateToManageFoodPlan = { navController.navigate(NavigationRoute.MANAGE_FOOD_PLAN_SCREEN.route) }
        )
    }
}

@Composable
fun Header(isListNotEmpty: Boolean, navigateToManageFoodPlan: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(top = 30.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = "My food plan",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold
        )
        if (isListNotEmpty) {
            Button(
                modifier = Modifier
                    .padding(10.dp)
                    .size(20.dp)
                    .shadow(elevation = 8.dp, shape = CircleShape)
                    .background(color = PrimaryColor),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = PrimaryColor,
                    contentColor = Color.White
                ),
                onClick = { navigateToManageFoodPlan() }
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_edit_white),
                    contentDescription = "Manage food plan",
                    modifier = Modifier.size(10.dp)
                )
            }
        }
    }
}

@Composable
fun DateBar(date: String, changeDate: (nextDay: Boolean) -> Unit) {
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth().padding(top = 2.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_select_prev),
            contentDescription = "Select previous day",
            modifier = Modifier
                .size(54.dp)
                .padding(vertical = 18.dp, horizontal = 10.dp)
                .clickable { changeDate(false) }
        )
        Text(
            modifier = Modifier.padding(vertical = 18.dp, horizontal = 10.dp),
            text = date,
            style = MyRationTypography.displayLarge,
            textAlign = TextAlign.Center
        )

        Image(
            painter = painterResource(id = R.drawable.ic_calendar),
            contentDescription = "calendar icon",
            modifier = Modifier
                .size(54.dp)
                .padding(vertical = 18.dp, horizontal = 10.dp)
                .clickable { changeDate(true) }
        )

        Image(
            painter = painterResource(id = R.drawable.ic_select_next),
            contentDescription = "Select next day",
            modifier = Modifier
                .size(54.dp)
                .padding(vertical = 18.dp, horizontal = 10.dp)
                .clickable { changeDate(true) }
        )
    }
}

@Composable
fun FoodPlanList(state: List<FoodPlan>, markFoodEaten: (plan: FoodPlan) -> Unit, navigateToManageFoodPlan: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize().padding(start = 20.dp, end = 20.dp, bottom = 20.dp)
    ) {
        LazyColumn {
            items(
                count = state.size,
                itemContent = { index ->
                    val shouldShowHeader = index == 0 || state[index].mealNumber != state[index - 1].mealNumber

                    if (shouldShowHeader) {
                        Text(
                            modifier = Modifier.fillMaxWidth().padding(vertical = 22.dp),
                            text = state[index].getMealtimesStr(),
                            style = MyRationTypography.headlineLarge,
                            textAlign = TextAlign.Center
                        )
                    }

                    FoodPlanItem(
                        item = state[index],
                        markFoodEaten
                    )
                }
            )
        }
        Spacer(modifier = Modifier.height(32.dp))
        if (state.isEmpty()) {
            CreatePlanBtn(navigateToManageFoodPlan)
        }
    }
}

@Composable
fun CreatePlanBtn(navigateToManageFoodPlan: () -> Unit) {
    Row(
        modifier = Modifier
            .padding(top = 50.dp)
            .fillMaxWidth()
            .shadow(elevation = 8.dp, shape = RoundedCornerShape(12.dp))
            .background(color = PrimaryColor, shape = RoundedCornerShape(12.dp)) // Matched corners
            .clickable { navigateToManageFoodPlan() }
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_manage_food_plan),
            contentDescription = "Manage food plan",
            modifier = Modifier
                .size(35.dp)
                .padding(4.dp)
        )
        Text(
            modifier = Modifier.padding(vertical = 20.dp, horizontal = 8.dp),
            text = "Create food plan",
            style = MyRationTypography.displayLarge,
            textAlign = TextAlign.Center,
            color = Color.White
        )
    }
}

@Composable
fun FoodPlanItem(item: FoodPlan, markFoodEaten: (plan: FoodPlan) -> Unit) {
    val isChecked = remember { mutableStateOf(item.completed) }
    val isExpanded = remember { mutableStateOf(false) }
    val expandIcon = if (isExpanded.value) R.drawable.ic_minimaze_24_black else R.drawable.ic_expand_more_24_black

    Box(
        modifier = Modifier.fillMaxWidth()
            .padding(14.dp)
            .shadow(elevation = 8.dp, shape = RoundedCornerShape(24.dp))
            .background(color = Color.White, shape = RoundedCornerShape(24.dp))
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
                    style = MyRationTypography.displayLarge,
                    textAlign = TextAlign.Center,
                    textDecoration = if (isChecked.value) TextDecoration.LineThrough else TextDecoration.None
                )
                Text(
                    text = "${item.mealCalorie} kcal",
                    style = MyRationTypography.displayLarge,
                    textAlign = TextAlign.Center,
                    textDecoration = if (isChecked.value) TextDecoration.LineThrough else TextDecoration.None
                )
                Checkbox(
                    checked = isChecked.value,
                    onCheckedChange = { isNewChecked ->
                        if (isNewChecked) {
                            isChecked.value = true
                            markFoodEaten(item)
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
                            text = " • ${ingredient.name}   ${ingredient.amountGrams} g.   source : ${ingredient.amountSource}",
                            style = MyRationTypography.labelMedium,
                            textDecoration = if (isChecked.value) TextDecoration.LineThrough else TextDecoration.None
                        )
                    }
                }
            }
        }
    }
}
