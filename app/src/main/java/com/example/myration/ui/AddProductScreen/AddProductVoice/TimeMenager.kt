package com.example.myration.ui.AddProductScreen.AddProductVoice

import android.os.Handler
import android.os.Looper
import android.util.Log

object TimerManager {
    private val handler = Handler(Looper.getMainLooper())
    private var runnable: Runnable? = null

    fun start(intervalMillis: Long, onTick: () -> Unit) {
        stop() // stop any existing one
        runnable = object : Runnable {
            override fun run() {
                onTick()
                handler.postDelayed(this, intervalMillis)
            }
        }
        handler.postDelayed(runnable!!, intervalMillis)
    }

    fun stop() {
        runnable?.let {
            handler.removeCallbacks(it)
            runnable = null
            Log.i("recording_log", "Timer stopped")
        }
    }
}
