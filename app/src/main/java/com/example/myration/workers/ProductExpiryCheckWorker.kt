package com.example.myration.workers

import android.Manifest
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.core_ui.R
import com.example.domain.usecase.GetSoonExpiredProductsUseCase
import kotlin.jvm.java
import com.example.myration.MainActivity
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class ProductExpiryCheckWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val checkUseCase: GetSoonExpiredProductsUseCase
) : CoroutineWorker(context, workerParams) {

    val productsExpNotifChannel = "PRODUCT_EXPIRY_CHANNEL_ID"

    override suspend fun doWork(): Result {
        val expProducts = checkUseCase.execute()

        if (expProducts.isNotEmpty()) {
            sendNotification(expProducts.size)
        }

        return Result.success()
    }

    private fun sendNotification(expAmountProduct: Int) {
        val intent = Intent(applicationContext, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

         val pendingIntent: PendingIntent = PendingIntent.getActivity(
            applicationContext,
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )

        val builder = NotificationCompat.Builder(applicationContext, productsExpNotifChannel)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle("Product expiration alert")
            .setContentText("You have $expAmountProduct products expiry soon")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        with(NotificationManagerCompat.from(applicationContext)) {
            if (ActivityCompat.checkSelfPermission(
                    applicationContext,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED ) { return }
            notify(1, builder.build())
        }
    }
}