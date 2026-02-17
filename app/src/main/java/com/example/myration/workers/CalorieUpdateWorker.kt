package com.example.myration.workers

import android.content.Context
import androidx.glance.appwidget.updateAll
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.myration.widgets.CalorieScreenWidget

class CalorieUpdateWorker(context: Context, params: WorkerParameters) : CoroutineWorker(context, params) {
    override suspend fun doWork(): Result {
        CalorieScreenWidget().updateAll(applicationContext)
        return Result.success()
    }
}
