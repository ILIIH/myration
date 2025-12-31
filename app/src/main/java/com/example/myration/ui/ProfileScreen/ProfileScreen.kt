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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.glance.appwidget.updateAll
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.ButtonDefaults
import com.example.annotations.DevicePreviews
import com.example.core_ui.custom_views.CalorieCounter
import com.example.data.model.maping.SDF
import com.example.domain.model.CalorieCounter
import com.example.domain.model.FoodHistory
import com.example.myration.mvi.effects.ProfileEffect
import com.example.myration.mvi.state.ProfileViewState
import com.example.myration.navigation.NavigationRoute
import com.example.myration.ui.RationHistory.FoodHistoryItem
import com.example.myration.viewModels.MainViewModel
import com.example.myration.viewModels.ProfileViewModel
import com.example.myration.widgets.CalorieScreenWidget
import com.example.theme.PrimaryColor
import com.example.theme.PrimaryLightColor
import com.example.theme.SecondaryColor
import com.example.theme.SecondaryHalfTransparentColor
import com.example.theme.MyRationTypography

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
                calorieInfo = state.info!!,
                foodHistory = state.foodHistory,
                showChangeMaxCalorieDialogue = viewModel::showChangeMaxCalorie,
                showAddEatenProductDialogue = viewModel::showAddEatenProduct,
                navigateToFoodHistory = {  navController.navigate(NavigationRoute.RATION_HISTORY_SCREEN.route) }
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
    if (showChangeMaxCalorieDialogue.value != null){
        ChangeMaxCalorieDialogue(
            maxCal = showChangeMaxCalorieDialogue.value!!,
            onDismiss = {showChangeMaxCalorieDialogue.value = null},
            onChange = { newCalorie ->
                viewModel.setNewMaxCalories(newCalorie){
                    CalorieScreenWidget().updateAll(context)
                }
                showChangeMaxCalorieDialogue.value = null
            }
        )
    }
    if(showAddEatenProductDialogue.value){
        AddEatenProductDialogue (
            onDismiss = {showAddEatenProductDialogue.value = false},
            onAdd = { productCalorie, productName, p, f, c ->
                viewModel.addEatenProduct(
                    productName = productName,
                    calorie = productCalorie, p = p, f = f, c =c
                ){
                    CalorieScreenWidget().updateAll(context)
                }
                showAddEatenProductDialogue.value = false
            }
        )
    }
}
@Composable
fun ProfileScreenLoaded(calorieInfo : CalorieCounter, foodHistory: List<FoodHistory>, showChangeMaxCalorieDialogue: () -> Unit, showAddEatenProductDialogue : () -> Unit, navigateToFoodHistory: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(color = SecondaryHalfTransparentColor),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CalorieInfoSection(calorieInfo,showChangeMaxCalorieDialogue )
        PFCSection(calorieInfo.protein, calorieInfo.fats, calorieInfo.carbohydrates)
        FoodHistorySection(showAddEatenProductDialogue, foodHistory, navigateToFoodHistory)
    }
}

@Composable
fun CalorieInfoSection(calorieInfo: CalorieCounter, showChangeMaxCalorieDialogue: () -> Unit)
{
    Box ( modifier = Modifier
        .fillMaxWidth()
        .padding(top = 90.dp)
        .padding(20.dp)
        .background(color = PrimaryLightColor, shape = RoundedCornerShape(4.dp))
    ) {
        CalorieCounter(
            currentCalorie = calorieInfo.currentCalorie,
            maxCalorie = calorieInfo.maxCalorie,
        )
        Button(
            onClick = showChangeMaxCalorieDialogue,
            modifier = Modifier
                .align (Alignment.BottomEnd)
                .offset(y = 20.dp, x = 20.dp)
                .zIndex(1f)
                .padding(10.dp)
                .shadow(elevation = 8.dp, shape = CircleShape)
                .background(color = PrimaryColor),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = PrimaryColor,
                contentColor = Color.White
            )
        ) {
            Image(
                painter = painterResource(id = com.example.myration.R.drawable.ic_change_max_cal),
                contentDescription = "Change max calorie icon",
                modifier = Modifier
                    .size(24.dp)
            )
        }
    }
}

@Composable
fun FoodHistorySection(showAddEatenProductDialogue: () -> Unit, foodHistory: List<FoodHistory>, navigateToFoodHistory: () -> Unit) {
    Box(modifier = Modifier.fillMaxWidth()
        .padding(20.dp)
        .padding(bottom = 90.dp)
        .background(color = PrimaryLightColor, shape = RoundedCornerShape(4.dp))
    ) {
        LazyColumn(
            modifier = Modifier.padding(10.dp).height(240.dp)
        ) {
            item{
                Text(
                    modifier = Modifier.fillMaxWidth().padding(10.dp),
                    text = "My ration history :",
                    style = MyRationTypography.displayLarge,
                    textAlign = TextAlign.Center
                )
            }
            items(
                count = foodHistory.size,
                itemContent = { index ->
                    FoodHistoryItem(foodHistory[index])
                }
            )
            item{
                Text(
                    text = ". . .",
                    style = MyRationTypography.displayLarge,
                    modifier = Modifier.fillMaxWidth().clickable{
                        navigateToFoodHistory()
                    },
                    textAlign = TextAlign.Center
                )
            }
        }
        Button(
            onClick = showAddEatenProductDialogue,
            modifier = Modifier
                .align (Alignment.BottomEnd)
                .offset(y = 20.dp, x = 20.dp)
                .zIndex(1f)
                .padding(top = 30.dp)
                .shadow(elevation = 8.dp, shape = CircleShape),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = PrimaryColor,
                    contentColor = Color.White )
        ) {
            Image(
                painter = painterResource(id = com.example.myration.R.drawable.ic_add_eaten_product),
                contentDescription = "Add eaten product icon",
                modifier = Modifier
                    .size(24.dp)
            )
        }
    }

}
@Composable
fun PFCSection(protein: Int,fats: Int, carbohydrates: Int ) {
    Row(
        modifier = Modifier
            .background(color =  PrimaryLightColor, shape = RoundedCornerShape(4.dp)),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ){
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
        showAddEatenProductDialogue = {},
        foodHistory = listOf(
            FoodHistory(
                id = 1,
                productName = "test",
                productCalorie = 200f,
                date = SDF.parse("2000-02-02")
            ),
            FoodHistory(
                id = 1,
                productName = "test2",
                productCalorie = 300f,
                date = SDF.parse("2000-02-02")
            ),
            FoodHistory(
                id = 3,
                productName = "test4",
                productCalorie = 330f,
                date = SDF.parse("2012-02-02")
            )
        ),
        navigateToFoodHistory = {}
    )
}
