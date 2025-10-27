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
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.example.theme.SecondaryColor
import com.example.theme.Typography

@Composable
fun CalorieCounter(
    currentCalorie: Float,
    maxCalorie: Float,
    productCalorie: Int  = 0
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
                .width(100.dp)
                .semantics{ contentDescription = "Calorie widget, shows currentCalorie = $currentCalorie and maxCalorie = $maxCalorie and productCalorie = $productCalorie" },
            currentCalorie = if(currentCalorie > maxCalorie) maxCalorie else currentCalorie,
            maxCalorie = maxCalorie,
            caloriesToEat = productCalorie
        )
        Column (
            modifier = Modifier.fillMaxHeight().padding(start = 30.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Text(
                text = buildAnnotatedString {
                    append("Max calories : ")
                    withStyle(style = SpanStyle(fontWeight = FontWeight.ExtraBold)) {
                        append("$maxCalorie ")
                    }
                    append("kcal")
                },
                style = Typography.displayMedium,
                color = SecondaryColor,
                modifier = Modifier.padding(horizontal = 20.dp).semantics{ contentDescription = "Max calories for day text" },
                textAlign = TextAlign.Justify
            )
            Text(
                text = buildAnnotatedString {
                    if(productCalorie!=0){
                        withStyle(style = SpanStyle(fontWeight = FontWeight.ExtraBold)) {
                            append("$currentCalorie : ")
                        }
                        append("kcal + ")
                        withStyle(style = SpanStyle(fontWeight = FontWeight.ExtraBold)) {
                            append("$productCalorie : ")
                        }
                        append("kcal")
                    } else {
                        append("Current calorie : ")
                        withStyle(style = SpanStyle(fontWeight = FontWeight.ExtraBold)) {
                            append("$currentCalorie : ")
                        }
                        append(" kcal ")
                    }
                },
                style = Typography.displayMedium,
                color = SecondaryColor,
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp),
                textAlign = TextAlign.Justify
            )
            Text(
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append("${maxCalorie - currentCalorie} ")
                    }
                    append("kcal left for today")
                },
                style = Typography.displayMedium,
                color = SecondaryColor,
                modifier = Modifier.padding(horizontal = 20.dp),
                textAlign = TextAlign.Justify
            )
        }
    }
}