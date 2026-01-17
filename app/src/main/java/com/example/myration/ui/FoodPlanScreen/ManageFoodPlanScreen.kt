package com.example.myration.ui.FoodPlanScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.ButtonDefaults
import com.example.annotations.DevicePreviews
import com.example.coreUi.R
import com.example.domain.model.FoodPlan
import com.example.domain.model.getMealtimesStr
import com.example.myration.mvi.effects.ManageFoodPlanEffect
import com.example.myration.mvi.state.ManageFoodPlanViewState
import com.example.myration.navigation.NavigationRoute
import com.example.myration.viewModels.MainViewModel
import com.example.myration.viewModels.ManageFoodPlanViewModel
import com.example.theme.MyRationTypography
import com.example.theme.PrimaryColor
import com.example.theme.SecondaryColor
import kotlin.math.absoluteValue
import kotlin.math.roundToInt

@Composable
fun ManageFoodPlanScreen(
    viewModel: ManageFoodPlanViewModel = hiltViewModel(),
    navController: NavHostController,
    mainViewModel: MainViewModel
) {
    val state = viewModel.state.collectAsState()
    val showPlanApproveDialog = remember { mutableStateOf<List<FoodPlan>?>(null) }

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is ManageFoodPlanEffect.NavigateToFoodPlan -> {
                    navController.navigate(NavigationRoute.FOOD_PLAN_SCREEN.route)
                }
                is ManageFoodPlanEffect.NavigateToFoodList -> {
                    navController.navigate(NavigationRoute.GROCERIES_LIST_TAB.route)
                }
                is ManageFoodPlanEffect.FoodPlanApproveEffect -> {
                    showPlanApproveDialog.value = effect.foodPlan
                }
            }
        }
    }
    when (val state = state.value) {
        is ManageFoodPlanViewState.Idle -> {
            mainViewModel.setLoading(false)
            ManageFoodPlanScreenLoaded(viewModel::createPlan)
            if (showPlanApproveDialog.value != null) {
                ApprovePlanDialogue(
                    foodPlans = showPlanApproveDialog.value!!,
                    onDismiss = { showPlanApproveDialog.value = null },
                    onConfirm = { foodPlan ->
                        showPlanApproveDialog.value = null
                        viewModel.saveFoodPlan(foodPlan)
                    }
                )
            }
        }
        is ManageFoodPlanViewState.Loading -> {
            mainViewModel.setLoading(true)
        }
        is ManageFoodPlanViewState.Error -> {
            mainViewModel.showError(message = state.message)
        }
    }
}

@Composable
fun ApprovePlanDialogue(
    foodPlans: List<FoodPlan>,
    onDismiss: () -> Unit,
    onConfirm: (foodPlan: List<FoodPlan>) -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(8.dp)
        ) {
            Column(
                modifier = Modifier.padding(20.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_baseline_close),
                    contentDescription = "close window button",
                    modifier = Modifier
                        .padding(top = 5.dp, start = 5.dp, end = 5.dp)
                        .size(32.dp)
                        .align(Alignment.End)
                        .clickable { onDismiss() }
                        .padding(2.dp)
                )
                Spacer(modifier = Modifier.height(32.dp))
                Text(
                    text = "Confirm Your Plan",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                Spacer(modifier = Modifier.height(32.dp))
                Text(
                    modifier = Modifier.padding(vertical = 4.dp)
                        .align(Alignment.Start),
                    text = foodPlans.firstOrNull()?.getMealtimesStr() + ':',
                    style = MyRationTypography.displayLarge
                )
                LazyColumn {
                    items(
                        count = foodPlans.size,
                        itemContent = { index ->
                            if (index != 0) {
                                if (foodPlans[index].mealNumber != foodPlans[index - 1].mealNumber) {
                                    Text(
                                        text = foodPlans[index].getMealtimesStr() + ':',
                                        style = MyRationTypography.displayLarge
                                    )
                                }
                            }
                            Row(
                                modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp, horizontal = 4.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(foodPlans[index].mealName)
                                Text("${foodPlans[index].mealCalorie} kcal", color = Color.Gray)
                            }
                        }
                    )
                }
                Spacer(modifier = Modifier.height(32.dp))
                Button(
                    onClick = { onConfirm(foodPlans) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.CenterHorizontally)
                        .padding(top = 10.dp)
                        .shadow(elevation = 8.dp, shape = RoundedCornerShape(12.dp))
                        .background(color = PrimaryColor, shape = RoundedCornerShape(4.dp)),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = PrimaryColor,
                        contentColor = Color.White
                    )
                ) {
                    Text(text = "Approve plan", color = Color.White, style = MyRationTypography.displayLarge)
                }
            }
        }
    }
}

@Composable
fun ManageFoodPlanScreenLoaded(submitPlan: (caloriesPerDay: Int, numberOfMeals: Int, foodPref: String) -> Unit) {
    var caloriesPerDay by rememberSaveable { mutableFloatStateOf(0f) }
    var numberOfMeals by rememberSaveable { mutableFloatStateOf(0f) }
    var foodPref by rememberSaveable { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.padding(top = 50.dp),
            text = "Food plan settings",
            style = MyRationTypography.displayLarge
        )
        Sliders(
            caloriesPerDay = caloriesPerDay,
            onCalorieUpdate = { newValue -> caloriesPerDay = newValue },
            numberOfMeals = numberOfMeals,
            numberOfMealsUpdate = { newValue -> numberOfMeals = newValue }
        )
        FoodPrefDropdown(
            setFoodPref = { pref -> foodPref = pref }
        )
        Button(
            onClick = {
                submitPlan(caloriesPerDay.roundToInt(), numberOfMeals.roundToInt(), foodPref)
            },
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally)
                .padding(top = 30.dp, start = 50.dp, end = 50.dp)
                .shadow(elevation = 8.dp, shape = RoundedCornerShape(12.dp))
                .background(color = PrimaryColor, shape = RoundedCornerShape(4.dp)),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = PrimaryColor,
                contentColor = Color.White
            )
        ) {
            Text(text = "Submit", color = Color.White)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FoodPrefDropdown(setFoodPref: (pref: String) -> Unit) {
    val expanded = remember { mutableStateOf(false) }
    val items = listOf("more proteins", "more salats", "vegan", "no dairy", "sweet treat")
    val selectedItem = remember { mutableStateOf("") }

    ExposedDropdownMenuBox(
        modifier = Modifier.padding(start = 50.dp, end = 50.dp, top = 20.dp),
        expanded = expanded.value,
        onExpandedChange = { expanded.value = !expanded.value }
    ) {
        TextField(
            value = selectedItem.value,
            onValueChange = {},
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor(),
            label = { Text("Food preferencies", style = MyRationTypography.displaySmall) },
            readOnly = true,
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded.value)
            },
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedContainerColor = Color.White,
                focusedContainerColor = Color.White
            )
        )

        ExposedDropdownMenu(
            expanded = expanded.value,
            onDismissRequest = { expanded.value = false }
        ) {
            items.forEach { pref ->
                DropdownMenuItem(
                    text = { Text(text = pref, style = MyRationTypography.displaySmall) },
                    onClick = {
                        selectedItem.value = pref
                        setFoodPref(pref)
                        expanded.value = false
                    }
                )
            }
        }
    }
}

@Composable
fun Sliders(caloriesPerDay: Float, onCalorieUpdate: (Float) -> Unit, numberOfMeals: Float, numberOfMealsUpdate: (Float) -> Unit) {
    Column(
        modifier = Modifier.padding(start = 50.dp, end = 50.dp, top = 80.dp)
    ) {
        Text(
            modifier = Modifier.padding(bottom = 10.dp),
            text = "Calories per day ${caloriesPerDay.absoluteValue.roundToInt()}",
            style = MyRationTypography.displaySmall
        )
        Slider(
            value = caloriesPerDay,
            onValueChange = { onCalorieUpdate(it) },
            valueRange = 0f..2500f,
            steps = 20,
            colors = SliderDefaults.colors(
                thumbColor = SecondaryColor,
                activeTrackColor = SecondaryColor,
                inactiveTrackColor = Color.LightGray,
                activeTickColor = Color.White,
                inactiveTickColor = Color.DarkGray
            )
        )
        Text(
            modifier = Modifier.padding(bottom = 10.dp, top = 20.dp),
            text = "Number of meals ${numberOfMeals.absoluteValue.roundToInt()}",
            style = MyRationTypography.displaySmall
        )
        Slider(
            value = numberOfMeals,
            onValueChange = { numberOfMealsUpdate(it) },
            valueRange = 0f..7f,
            steps = 7,
            colors = SliderDefaults.colors(
                thumbColor = SecondaryColor,
                activeTrackColor = SecondaryColor,
                inactiveTrackColor = Color.LightGray,
                activeTickColor = Color.White,
                inactiveTickColor = Color.DarkGray
            )
        )
    }
}

@DevicePreviews
@Preview(showBackground = true)
@Composable
fun ManageFoodPlanScreenPreview() {
    ManageFoodPlanScreenLoaded({ a, b, c -> })
}
