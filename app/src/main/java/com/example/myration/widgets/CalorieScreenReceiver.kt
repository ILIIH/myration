package com.example.myration.widgets

import android.content.Context
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.myration.workers.CalorieUpdateWorker
import java.util.concurrent.TimeUnit

class CalorieScreenReceiver : GlanceAppWidgetReceiver() {
    override val glanceAppWidget: GlanceAppWidget = CalorieScreenWidget()

    override fun onEnabled(context: Context) {
        super.onEnabled(context)
        val workRequest = PeriodicWorkRequestBuilder<CalorieUpdateWorker>(1, TimeUnit.HOURS)
            .build()

        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            "calorie_widget_update",
            ExistingPeriodicWorkPolicy.KEEP,
            workRequest
        )
    }

    override fun onDisabled(context: Context) {
        super.onDisabled(context)
        WorkManager.getInstance(context).cancelUniqueWork("calorie_widget_update")
    }
}
