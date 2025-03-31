package com.example.core.android

import android.content.Context
import androidx.camera.lifecycle.ProcessCameraProvider
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.suspendCancellableCoroutine
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

suspend fun Context.getCameraProvider(): ProcessCameraProvider {
    return suspendCoroutine { cont ->
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)

        cameraProviderFuture.addListener({
            try {
                cont.resume(cameraProviderFuture.get())
            } catch (e: Exception) {
                throw RuntimeException("CameraProvider failed", e)
            }
        }, getExecutor())
    }
}

private fun Context.getExecutor(): Executor = Executors.newSingleThreadExecutor()