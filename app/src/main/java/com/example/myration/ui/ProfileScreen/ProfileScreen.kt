package com.example.myration.ui.ProfileScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.glance.appwidget.updateAll
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.ButtonDefaults
import com.example.annotations.DevicePreviews
import com.example.coreUi.customViews.CalorieCounter
import com.example.domain.model.CalorieCounter
import com.example.domain.model.PieChartItem
import com.example.myration.mvi.effects.ProfileEffect
import com.example.myration.mvi.state.ProfileViewState
import com.example.myration.navigation.NavigationRoute
import com.example.myration.ui.RationHistory.MonthSummaryInfo
import com.example.myration.viewModels.MainViewModel
import com.example.myration.viewModels.ProfileViewModel
import com.example.myration.widgets.CalorieScreenWidget
import com.example.theme.MyRationTypography
import com.example.theme.PrimaryColor
import com.example.theme.PrimaryLightColor
import com.example.theme.SecondaryColor
import com.example.theme.SecondaryHalfTransparentColor

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = hiltViewModel(),
    navController: NavHostController,
    mainViewModel: MainViewModel
) {
    val profileState by viewModel.state.collectAsState()
    val showChangeMaxCalorieDialogue = remember { mutableStateOf<Float?>(null) }
    val showAddEatenProductDialogue = remember { mutableStateOf(false) }
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.refreshInfo()
        showAddEatenProductDialogue.value = false
        viewModel.effect.collect { effect ->
            when (effect) {
                is ProfileEffect.ShowProfileChangeMaxCalorieWidget -> {
                    showChangeMaxCalorieDialogue.value = effect.maxCal
                }
                is ProfileEffect.ShowProfileAddEatenProductWidget -> {
                    showAddEatenProductDialogue.value = true
                }
            }
        }
    }

    when (val state = profileState) {
        is ProfileViewState.ProfileLoaded -> {
            mainViewModel.setLoading(false)
            ProfileScreenLoaded(
                calorieInfo = state.info,
                showChangeMaxCalorieDialogue = viewModel::showChangeMaxCalorie,
                openFoodPlan = { navController.navigate(NavigationRoute.FOOD_PLAN_SCREEN.route) },
                rationSummary = state.foodSummary
            )
        }
        is ProfileViewState.ProfileInfoSetUp -> {
            SetUpProfileDialogue(viewModel::setNewMaxCalories, viewModel::calculateMaxCalories)
        }
        is ProfileViewState.ProfileInfoError -> {
            mainViewModel.showError(message = state.message)
        }
        is ProfileViewState.ProfileLoading -> {
            mainViewModel.setLoading(true)
        }
    }

    // Dialogues
    // TODO("Fix the logic when have time")
    if (showChangeMaxCalorieDialogue.value != null) {
        ChangeMaxCalorieDialogue(
            maxCal = showChangeMaxCalorieDialogue.value!!,
            onDismiss = { showChangeMaxCalorieDialogue.value = null },
            onChange = { newCalorie ->
                viewModel.setNewMaxCalories(newCalorie) {
                    CalorieScreenWidget().updateAll(context)
                }
                showChangeMaxCalorieDialogue.value = null
            }
        )
    }
    if (showAddEatenProductDialogue.value) {
        navController.navigate(NavigationRoute.ADD_EATEN_PRODUCT_SCREEN.route)
    }
}

@Composable
fun ProfileScreenLoaded(
    calorieInfo: CalorieCounter,
    showChangeMaxCalorieDialogue: () -> Unit,
    openFoodPlan: () -> Unit,
    rationSummary: List<PieChartItem>
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(color = SecondaryHalfTransparentColor),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CalorieInfoSection(calorieInfo, showChangeMaxCalorieDialogue, calorieInfo.protein, calorieInfo.fats, calorieInfo.carbohydrates)
        FoodPlanSection(openFoodPlan)
        FoodHistorySection(rationSummary)
    }
}

@Composable
fun FoodPlanSection(openFoodPlan: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp)
            .padding(20.dp)
            .shadow(elevation = 2.dp, shape = RoundedCornerShape(12.dp))
            .background(color = PrimaryLightColor, shape = RoundedCornerShape(12.dp))
            .clickable { openFoodPlan() },
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.padding(10.dp),
            text = "Food plan",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            style = MyRationTypography.displayMedium
        )

        Image(
            painter = painterResource(id = com.example.coreUi.R.drawable.ic_food_plan),
            contentDescription = "My food plan",
            modifier = Modifier.size(24.dp)
        )
    }
}

@Composable
fun CalorieInfoSection(
    calorieInfo: CalorieCounter,
    showChangeMaxCalorieDialogue: () -> Unit,
    protein: Int,
    fats: Int,
    carbohydrates: Int
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 90.dp)
            .padding(start = 20.dp, end = 20.dp)
            .shadow(elevation = 2.dp, shape = RoundedCornerShape(12.dp))
            .background(color = Color.White, shape = RoundedCornerShape(12.dp))
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CalorieCounter(
                currentCalorie = calorieInfo.currentCalorie,
                maxCalorie = calorieInfo.maxCalorie
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp, top = 20.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center

            ) {
                Text(
                    modifier = Modifier.padding(10.dp),
                    text = "P : $protein",
                    style = MyRationTypography.displayLarge,
                    color = SecondaryColor
                )
                Text(
                    modifier = Modifier.padding(10.dp),
                    text = "F :  $fats",
                    style = MyRationTypography.displayLarge,
                    color = SecondaryColor
                )
                Text(
                    modifier = Modifier.padding(10.dp),
                    text = "C :  $carbohydrates",
                    style = MyRationTypography.displayLarge,
                    color = SecondaryColor
                )
            }
            Button(
                onClick = showChangeMaxCalorieDialogue,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
                    .shadow(elevation = 8.dp, shape = RoundedCornerShape(6.dp))
                    .background(color = PrimaryColor, shape = RoundedCornerShape(6.dp)),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = PrimaryColor,
                    contentColor = Color.White
                )
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Image(
                        painter = painterResource(id = com.example.myration.R.drawable.ic_change_max_cal),
                        contentDescription = "Change max calorie icon",
                        modifier = Modifier
                            .size(24.dp)
                    )
                    Text(
                        modifier = Modifier.padding(10.dp),
                        text = "Change max calorie",
                        style = MyRationTypography.displayLarge,
                        color = Color.White
                    )
                }
            }
        }
    }
}

@Composable
fun FoodHistorySection(rationSummary: List<PieChartItem>) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 90.dp)
    ) {
        MonthSummaryInfo(
            isMonthlySummary = false,
            monthlyFailSuccessList = rationSummary
        )
    }
}

@DevicePreviews
@Preview(showBackground = true)
@Composable
fun ProfileScreenLoadedPreview() {
    ProfileScreenLoaded(
        calorieInfo = CalorieCounter(
            maxCalorie = 2000f,
            currentCalorie = 1000f,
            protein = 100,
            fats = 100,
            carbohydrates = 100
        ),
        showChangeMaxCalorieDialogue = {},
        openFoodPlan = {},
        rationSummary = listOf()
    )
}
