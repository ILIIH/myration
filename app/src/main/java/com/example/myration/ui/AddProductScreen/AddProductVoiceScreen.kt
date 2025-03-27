package com.example.myration.ui.AddProductScreen

import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.myration.R
import com.example.myration.ui.theme.SecondaryBackgroundColor

@Composable
fun AddProductVoiceScreen() {
    var recordingProgress by remember { mutableStateOf(0f) }
    var isRecording by remember { mutableStateOf(false) }
    val maxRecordLength = 120f

    if (isRecording) {
        TimerEffect(
            intervalMillis = 100L,
            stop = !isRecording,
            onTick = {
                if (recordingProgress < maxRecordLength) {
                    recordingProgress += 10f
                }
            }
        )
    }
    else {
        recordingProgress = 0f
    }
    if (recordingProgress >= maxRecordLength) {
        isRecording = false
    }
    Box (modifier =  Modifier
        .fillMaxSize()
        .background(SecondaryBackgroundColor)){
        Row(
            modifier = Modifier
                .padding(horizontal = 20.dp, vertical = 40.dp)
                .border(width = 1.dp, color = Color.White, shape = RoundedCornerShape(12.dp))
                .background(Color.White, shape = RoundedCornerShape(12.dp))
                .padding(horizontal = 12.dp, vertical = 18.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterHorizontally)
        ) {
            Image(
                painter = painterResource(id =
                    if(!isRecording) R.drawable.ic_start_recording
                    else  R.drawable.ic_stop_recording
                ),
                contentDescription = "Record audio",
                modifier = Modifier
                    .size(50.dp)
                    .clickable {
                        isRecording = !isRecording
                    }
            )
            LinearProgressIndicator(progress = recordingProgress / maxRecordLength)
        }
    }

}

@Composable
fun TimerEffect(intervalMillis: Long, onTick: () -> Unit, stop: Boolean = false) {
    val handler = remember { Handler(Looper.getMainLooper()) }

    val runnable = rememberUpdatedState(object : Runnable {
        override fun run() {
            onTick()
            if (!stop) {
                handler.postDelayed(this, intervalMillis)
            }
        }
    })

    LaunchedEffect(stop) {
        if (!stop) {
            handler.postDelayed(runnable.value, intervalMillis)
        } else {
            handler.removeCallbacks(runnable.value)
        }
    }
}
