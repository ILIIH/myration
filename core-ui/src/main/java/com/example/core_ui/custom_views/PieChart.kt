package com.example.core_ui.custom_views

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import com.example.domain.model.PieChartItem
import com.example.theme.NegativeNegativeColor
import com.example.theme.PrimaryColor
import com.example.theme.SecondaryColor

@Composable
fun PieChart(
    modifier: Modifier = Modifier,
    items: List<PieChartItem>,
    isDonut: Boolean = false
) {
    val totalAmount = items.sumOf { it.amount }.toFloat()
    val animationPlayed = remember { mutableStateOf(false) }
    val animatedProgress by animateFloatAsState(
        targetValue = if (animationPlayed.value) 1f else 0f,
        animationSpec = tween(
            durationMillis = 1000,
            easing = LinearOutSlowInEasing
        )
    )

    LaunchedEffect(key1 = items) {
        animationPlayed.value = true
    }

    Canvas(modifier = modifier) {
        val baseChartSize = size.minDimension / 2f
        var currentStartAngle = -90f
        val gapPadding = 2f

        items.forEachIndexed { index, item ->
            val sweepAngle = (item.amount / totalAmount) * 360f

            val topLeft = Offset(
                x = (size.width - baseChartSize) / 2,
                y = (size.height - baseChartSize) / 2
            )
            val arcSize = Size(baseChartSize, baseChartSize)

            drawArc(
                color = Color(item.color),
                startAngle = currentStartAngle + (gapPadding / 2),
                sweepAngle = (sweepAngle - gapPadding) * animatedProgress,
                useCenter = !isDonut,
                topLeft = topLeft,
                size = arcSize,
                style = if (isDonut) {
                    Stroke(
                        width = baseChartSize / 5,
                    )
                } else Fill
            )

            currentStartAngle += sweepAngle
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PieChartPreview(){
    PieChart(
        modifier = Modifier.fillMaxWidth().aspectRatio(1f),
        items = listOf(
            PieChartItem("Protein", 30, PrimaryColor.hashCode()),
            PieChartItem("Carbs", 50, SecondaryColor.hashCode()),
            PieChartItem("Fat", 20, NegativeNegativeColor.hashCode())
        ),
        isDonut = true
    )
}
