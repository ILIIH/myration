package com.example.myration.ui.ProfileScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.unit.dp
import androidx.glance.appwidget.updateAll
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.ButtonDefaults
import com.example.core_ui.calorie_counter.CalorieCounter
import com.example.myration.viewModels.ProfileViewModel
import com.example.myration.widgets.CalorieScreenWidget
import com.example.theme.PrimaryColor
import com.example.theme.SecondaryColor
import com.example.theme.Typography

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val calorieInfo by viewModel.calorie.collectAsState()
    val showChangeMaxCalorieDialogue = remember { mutableStateOf(false) }
    val showAddEatenProductDialogue = remember { mutableStateOf(false) }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Row(
            modifier = Modifier
                .padding( top = 100.dp, bottom = 60.dp)
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ){
            Text(
                modifier = Modifier.padding(10.dp),
                text = "ILLIA",
                style = Typography.displayLarge,
                color = SecondaryColor
            )
            Text(
                modifier = Modifier.padding(10.dp),
                text = "BRANCHUK",
                style = Typography.displayLarge,
                color = SecondaryColor
            )
        }
        CalorieCounter(
            currentCalorie = calorieInfo.currentCalorie,
            maxCalorie = calorieInfo.maxCalorie,
            productCalorie = null
        )
        Row(
            modifier = Modifier
                .padding(40.dp)
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ){
            Text(
                modifier = Modifier.padding(10.dp),
                text = "P : ${calorieInfo.protein}",
                style = Typography.displayLarge,
                color = SecondaryColor
            )
            Text(
                modifier = Modifier.padding(10.dp),
                text = "F :  ${calorieInfo.fats}",
                style = Typography.displayLarge,
                color = SecondaryColor
            )
            Text(
                modifier = Modifier.padding(10.dp),
                text = "C :  ${calorieInfo.carbohydrates}",
                style = Typography.displayLarge,
                color = SecondaryColor
            )
        }
        Button(
            onClick = {
                showChangeMaxCalorieDialogue.value = true
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp)
                .padding(top = 30.dp)
                .shadow(elevation = 8.dp, shape = RoundedCornerShape(12.dp))
                .background(color = PrimaryColor, shape = RoundedCornerShape(4.dp)),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = PrimaryColor,
                contentColor = Color.White
            )
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            )
            {
                Text(text = "Change max calorie", color = Color.White)
                Image(
                    painter = painterResource(id = com.example.myration.R.drawable.ic_change_max_cal),
                    contentDescription = "Change max calorie icon",
                    modifier = Modifier
                        .padding(start = 10.dp)
                        .size(24.dp)
                )
            }
        }
        Button(
            onClick = {
                showAddEatenProductDialogue.value = true
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp)
                .padding(top = 30.dp)
                .shadow(elevation = 8.dp, shape = RoundedCornerShape(12.dp))
                .background(color = SecondaryColor, shape = RoundedCornerShape(4.dp)),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = SecondaryColor,
                contentColor = Color.White
            )
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            )
            {
                Text(text = "Add eaten product", color = Color.White)
                Image(
                    painter = painterResource(id = com.example.myration.R.drawable.ic_add_eaten_product),
                    contentDescription = "Add eaten product icon",
                    modifier = Modifier
                        .padding(start = 10.dp)
                        .size(24.dp)
                )
            }
        }
    }

    if(showChangeMaxCalorieDialogue.value){
        ChangeMaxCalorieDialogue(
            maxCal = calorieInfo.maxCalorie,
            onDismiss = {showChangeMaxCalorieDialogue.value = false},
            onChange = { newCalorie ->
                viewModel.setNewMaxCalories(newCalorie){
                    CalorieScreenWidget().updateAll(context)
                }
                showChangeMaxCalorieDialogue.value = false
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

