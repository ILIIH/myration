package com.example.core_ui.calorie_counter

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.theme.SecondaryColor
import com.example.theme.Typography

@Composable
fun CalorieCounter(
    currentCalorie: Float,
    maxCalorie: Float,
    productCalorie: Int?
) {
    Row (
        modifier = Modifier.fillMaxWidth()
            .height(140.dp)
            .padding(start = 30.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ){
        CalorieCounterWidget(
            modifier = Modifier
                .height(100.dp)
                .width(100.dp),
            currentCalorie = if(currentCalorie > maxCalorie) maxCalorie else currentCalorie,
            maxCalorie = maxCalorie
        )
        Column (
            modifier = Modifier.fillMaxHeight().padding(start = 30.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Text(
                text = "Max calories : $maxCalorie kcal" ,
                style = Typography.displayMedium,
                color = SecondaryColor,
                modifier = Modifier.padding(horizontal = 20.dp),
                textAlign = TextAlign.Justify
            )
            Text(
                text = if(productCalorie!=null) "$currentCalorie kcal + $productCalorie kcal" else  " Current calorie : $currentCalorie kcal",
                style = Typography.displayMedium,
                color = SecondaryColor,
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp),
                textAlign = TextAlign.Justify
            )
            Text(
                text = "${maxCalorie - currentCalorie} kcal left for today" ,
                style = Typography.displayMedium,
                color = SecondaryColor,
                modifier = Modifier.padding(horizontal = 20.dp),
                textAlign = TextAlign.Justify
            )
        }
    }
}