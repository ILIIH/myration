package com.example.coreUi.customViews

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
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
    val currentCalorieColor = if (currentCalorie < maxCalorie) SecondaryColor else NegativeNegativeColor
    val caloriesToEatColor = if (currentCalorie + caloriesToEat < maxCalorie) PrimaryColor else NegativeNegativeColor

    val currentCaloriesAngle = ((currentCalorie * 180) / maxCalorie)
    val animatedCurrentCalorieAngle by animateFloatAsState(
        targetValue = currentCaloriesAngle,
        animationSpec = tween(
            durationMillis = 1000,
            easing = LinearOutSlowInEasing
        )
    )
    val caloriesToEatAngle = if (caloriesToEat + currentCalorie >= maxCalorie) 170f - currentCaloriesAngle else ((caloriesToEat * 180) / maxCalorie)
    val animatedCaloriesToEatAngle by animateFloatAsState(
        targetValue = caloriesToEatAngle,
        animationSpec = tween(
            durationMillis = 1000,
            easing = LinearOutSlowInEasing
        )
    )

    Canvas(modifier = modifier) {
        val arcStrokeWidth = size.width / 3
        val secondaryArcStrokeWidth = size.width / 4
        val arcSize = Size(width = size.width, height = size.height)

        drawArc(
            color = SecondaryHalfTransparentColor,
            startAngle = 180f,
            sweepAngle = 180f,
            useCenter = false,
            topLeft = Offset.Zero,
            size = arcSize,
            style = Stroke(width = arcStrokeWidth)
        )

        drawArc(
            color = currentCalorieColor,
            startAngle = 180f,
            sweepAngle = animatedCurrentCalorieAngle,
            useCenter = false,
            topLeft = Offset.Zero,
            size = arcSize,
            style = Stroke(width = secondaryArcStrokeWidth)
        )

        if (caloriesToEat > 0) {
            drawArc(
                color = caloriesToEatColor,
                startAngle = 180f + animatedCurrentCalorieAngle + if (currentCaloriesAngle == 0f) 0f else 6f,
                sweepAngle = animatedCaloriesToEatAngle,
                useCenter = false,
                topLeft = Offset.Zero,
                size = arcSize,
                style = Stroke(width = secondaryArcStrokeWidth)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CalorieCounterWidgetPreview() {
    CalorieCounterWidget(Modifier.padding(0.dp), 2000f, 4000f, 200)
}
