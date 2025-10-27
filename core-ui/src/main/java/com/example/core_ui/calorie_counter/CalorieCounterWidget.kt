package com.example.core_ui.calorie_counter

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import com.example.theme.NegativeNegativeColor
import com.example.theme.PrimaryColor
import com.example.theme.SecondaryColor
import com.example.theme.SecondaryHalfTransparentColor

@Composable
fun CalorieCounterWidget(
    modifier: Modifier,
    currentCalorie: Float,
    maxCalorie: Float,
    caloriesToEat: Int
) {
    val currentCalorieColor = if(currentCalorie < maxCalorie ) SecondaryColor else NegativeNegativeColor
    val caloriesToEatColor = if(currentCalorie + caloriesToEat < maxCalorie ) PrimaryColor else NegativeNegativeColor

    val currentCaloriesAngle = ((currentCalorie * 180) / maxCalorie)
    val animatedCurrentCalorieAngle by animateFloatAsState(
        targetValue = currentCaloriesAngle,
        animationSpec = tween(
            durationMillis = 1000,
            easing = LinearOutSlowInEasing
        )
    )
    val caloriesToEatAngle = if(caloriesToEat +currentCalorie >= maxCalorie ) 170f - currentCaloriesAngle else ((caloriesToEat * 180) / maxCalorie)
    val animatedCaloriesToEatAngle by animateFloatAsState(
        targetValue = caloriesToEatAngle,
        animationSpec = tween(
            durationMillis = 1000,
            easing = LinearOutSlowInEasing
        )
    )

    Canvas(modifier = modifier.fillMaxWidth()) {
        val arcStrokeWidth = size.width / 3
        val secondaryArcStrokeWidth = size.width / 4
        val arcSize = Size(width = size.width, height = size.height)
        val radius = size.width / 2
        val centerX = size.width / 2
        val centerY = size.height / 2

        drawArc(
            color = SecondaryHalfTransparentColor,
            startAngle = 180f,
            sweepAngle = 180f,
            useCenter = false,
            topLeft =  Offset.Zero,
            size = arcSize,
            style = Stroke(width = arcStrokeWidth)
        )

        drawArc(
            color = currentCalorieColor,
            startAngle = 180f,
            sweepAngle = animatedCurrentCalorieAngle,
            useCenter = false,
            topLeft =  Offset.Zero,
            size = arcSize,
            style = Stroke(width = secondaryArcStrokeWidth)
        )

        if(caloriesToEat > 0){
            drawArc(
                color = caloriesToEatColor,
                startAngle = 180f + animatedCurrentCalorieAngle + if(currentCaloriesAngle == 0f) 0f else 6f,
                sweepAngle = animatedCaloriesToEatAngle,
                useCenter = false,
                topLeft =  Offset.Zero,
                size = arcSize,
                style = Stroke(width = secondaryArcStrokeWidth)
            )
        }

        drawContext.canvas.nativeCanvas.apply {
            val text = "$currentCalorie KCAL"
            val paint = android.graphics.Paint().apply {
                color = SecondaryColor.toArgb()
                textSize = 65f
                textAlign = android.graphics.Paint.Align.CENTER
                isAntiAlias = true
                typeface = android.graphics.Typeface.create(
                    android.graphics.Typeface.DEFAULT,
                    android.graphics.Typeface.BOLD
                )
            }

            drawText(
                text,
                centerX,
                centerY + radius / 1.5f,
                paint
            )
        }
    }
}

