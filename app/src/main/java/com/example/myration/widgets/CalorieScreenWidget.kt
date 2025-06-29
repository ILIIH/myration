package com.example.myration.widgets

import android.content.Context
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.action.actionStartActivity
import androidx.glance.action.clickable
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.LinearProgressIndicator
import androidx.glance.appwidget.provideContent
import androidx.glance.background
import androidx.glance.layout.Alignment
import androidx.glance.layout.Box
import androidx.glance.layout.Column
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.fillMaxWidth
import androidx.glance.layout.padding
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import androidx.glance.unit.ColorProvider
import com.example.data.repository.CalorieRepositoryImp
import com.example.myration.MainActivity

class CalorieScreenWidget  : GlanceAppWidget() {

    override suspend fun provideGlance(context: Context, id: GlanceId) {
        val preferences = context.getSharedPreferences(CalorieRepositoryImp.PREF_NAME, Context.MODE_PRIVATE)
        val maxCalorie = preferences.getFloat(CalorieRepositoryImp.MAX_CALORIE, CalorieRepositoryImp.DEFAULT_MAX_CALORIE)
        val currentCalorie = preferences.getFloat(CalorieRepositoryImp.CURRENT_CALORIE, CalorieRepositoryImp.DEFAULT_CURRENT_CALORIE)
        val progress = currentCalorie / maxCalorie

        provideContent {
            Box(
                modifier = GlanceModifier
                    .fillMaxWidth()
                    .clickable(
                        actionStartActivity<MainActivity>()
                    )
                    .padding(12.dp)
                    .background(Color.LightGray)
            ) {
                Column(
                    modifier = GlanceModifier
                        .fillMaxSize()
                        .background(color = Color(0xFFFFFFFF)) ,
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Calories",
                        style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    )
                    Text(
                        text = "${currentCalorie.toInt()} / ${maxCalorie.toInt()} KCAL",
                        style = TextStyle(fontSize = 16.sp)
                    )
                    LinearProgressIndicator(
                        progress = progress,
                        color = ColorProvider( Color(0xFF499F68))
                    )
                }
            }
        }
    }
}

