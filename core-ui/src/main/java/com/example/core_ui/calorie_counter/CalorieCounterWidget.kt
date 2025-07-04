package com.example.core_ui.calorie_counter

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import com.example.theme.SecondaryColor
import com.example.theme.SecondaryHalfTransparentColor

@Composable
fun CalorieCounterWidget(
    modifier: Modifier,
    currentCalorie: Float,
    maxCalorie: Float,
) {
    val currentCaloriesAngle = ((currentCalorie * 180) / maxCalorie)
    Canvas(modifier = modifier.fillMaxWidth()) {
        val arcStrokeWidth = size.width / 3
        val arcSize = Size(width = size.width, height = size.height)
        val radius = size.width / 2
        val centerX = size.width / 2
        val centerY = size.height / 2

        drawArc(
            color = SecondaryColor,
            startAngle = 180f,
            sweepAngle = currentCaloriesAngle,
            useCenter = false,
            topLeft =  Offset.Zero,
            size = arcSize,
            style = Stroke(width = arcStrokeWidth)
        )

        drawArc(
            color = SecondaryHalfTransparentColor,
            startAngle = 180f + currentCaloriesAngle,
            sweepAngle = 180f - currentCaloriesAngle,
            useCenter = false,
            topLeft =  Offset.Zero,
            size = arcSize,
            style = Stroke(width = arcStrokeWidth)
        )

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
