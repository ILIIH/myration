package com.example.myration.ui.FoodPlanScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.ButtonDefaults
import com.example.domain.model.getMealtimesStr
import com.example.myration.navigation.NavigationRoute
import com.example.myration.viewModels.FoodPlanViewModel
import com.example.myration.viewModels.MainViewModel
import com.example.theme.MyRationTypography
import com.example.theme.PrimaryColor

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
        Text(
            modifier = Modifier.padding(vertical = 4.dp),
            text = state.value.firstOrNull()?.getMealtimesStr() + ':',
            style = MyRationTypography.displayLarge
        )
        LazyColumn {
            items(
                count = state.value.size,
                itemContent = { index ->
                    if (index != 0) {
                        if (state.value[index].mealNumber != state.value[index - 1].mealNumber) {
                            Text(
                                text = state.value[index].getMealtimesStr() + ':',
                                style = MyRationTypography.displayLarge
                            )
                        }
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(state.value[index].mealName)
                        Text(state.value[index].amountGramsIng + " g. ")
                        Text("${state.value[index].mealCalorie} kcal", color = Color.Gray)
                    }
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
