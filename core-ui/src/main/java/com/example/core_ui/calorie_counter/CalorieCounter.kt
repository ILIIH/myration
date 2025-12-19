package com.example.core_ui.calorie_counter

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.theme.SecondaryColor
import com.example.theme.Typography

@Composable
fun CalorieCounter(
    currentCalorie: Float,
    maxCalorie: Float,
    productCalorie: Int  = 0,
) {
    Row (
        modifier = Modifier.fillMaxWidth()
            .padding(30.dp)
            .height(140.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ){
        Column (
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ){
            CalorieCounterWidget(
                modifier = Modifier
                    .height(80.dp)
                    .width(80.dp)
                    .semantics{ contentDescription = "Calorie widget, shows currentCalorie = $currentCalorie and maxCalorie = $maxCalorie and productCalorie = $productCalorie" },
                currentCalorie = if(currentCalorie > maxCalorie) maxCalorie else currentCalorie,
                maxCalorie = maxCalorie,
                caloriesToEat = if(currentCalorie > maxCalorie) 0 else productCalorie
            )
            Text(
                text = "${currentCalorie.toInt()} KCAL",
                style = Typography.displayLarge,
                color = SecondaryColor,
            )
        }

        Column (
            modifier = Modifier.fillMaxHeight().padding(start = 30.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Text(
                text = buildAnnotatedString {
                    append("Max calories : ")
                    withStyle(style = SpanStyle(fontWeight = FontWeight.ExtraBold)) {
                        append("${maxCalorie.toInt()}")
                    }
                    append("kcal")
                },
                style = Typography.displaySmall,
                color = SecondaryColor,
                modifier = Modifier.padding(horizontal = 20.dp).semantics{ contentDescription = "Max calories for day text" },
                textAlign = TextAlign.Justify
            )
            Text(
                text = buildAnnotatedString {
                    if(productCalorie!=0){
                        withStyle(style = SpanStyle(fontWeight = FontWeight.ExtraBold)) {
                            append("${currentCalorie.toInt()} : ")
                        }
                        append("kcal + ")
                        withStyle(style = SpanStyle(fontWeight = FontWeight.ExtraBold)) {
                            append("$productCalorie : ")
                        }
                        append("kcal")
                    } else {
                        append("Calories now : ")
                        withStyle(style = SpanStyle(fontWeight = FontWeight.ExtraBold)) {
                            append("${currentCalorie.toInt()} ")
                        }
                        append(" kcal ")
                    }
                },
                style = Typography.displaySmall,
                color = SecondaryColor,
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp),
                textAlign = TextAlign.Justify
            )
            Text(
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append("${(maxCalorie - currentCalorie).toInt()} ")
                    }
                    append("kcal left for today")
                },
                style = Typography.displaySmall,
                color = SecondaryColor,
                modifier = Modifier.padding(horizontal = 20.dp),
                textAlign = TextAlign.Justify
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CalorieCounterPreview(){
    CalorieCounter(2000f,4000f, 200)
}