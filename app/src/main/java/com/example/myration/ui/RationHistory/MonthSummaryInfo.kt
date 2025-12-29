package com.example.myration.ui.RationHistory

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.core_ui.custom_views.PieChart
import com.example.domain.model.PieChartItem
import com.example.theme.MyRationTypography
import com.example.theme.PrimaryLightColor
import com.example.theme.SecondaryColor
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun MonthSummaryInfo(monthlyFailSuccessList: List<PieChartItem> , date: Date ){
    val monthName = SimpleDateFormat("MMMM", Locale.getDefault()).format(date)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
            .border(width = 1.dp, color = Color.Black, shape = RoundedCornerShape(12.dp))
            .shadow(elevation = 8.dp, shape = RoundedCornerShape(8.dp))
            .background(
                color = PrimaryLightColor,
                shape = RoundedCornerShape(12.dp)
            ))
    {
        Column{
            Text(
                modifier = Modifier.fillMaxWidth().padding(top = 20.dp),
                textAlign = TextAlign.Center,
                text = monthName,
                style = MyRationTypography.displayLarge,
                color = SecondaryColor
            )
            PieChart(
                modifier = Modifier.fillMaxWidth().aspectRatio(1f),
                items = monthlyFailSuccessList,
                isDonut = true
            )
            Column(
                modifier =  Modifier.padding(bottom = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ){
                for (monthlySummary in monthlyFailSuccessList){
                    Text(
                        modifier = Modifier.fillMaxWidth().padding(top = 10.dp),
                        textAlign = TextAlign.Center,
                        text = monthlySummary.label,
                        style = MyRationTypography.displayMedium,
                        color = SecondaryColor
                    )
                }
            }
        }
    }
}