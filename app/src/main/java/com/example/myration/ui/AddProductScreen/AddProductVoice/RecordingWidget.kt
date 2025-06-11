package com.example.myration.ui.AddProductScreen.AddProductVoice

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.myration.R

@Composable
fun RecordingWidget(
    isRecording: Boolean,
    recordingProgress: Float,
    maxRecordLength: Float,
    startRecording: () -> Unit,
    stopRecording: () -> Unit
) {
    Row(
        modifier = Modifier
            .padding(horizontal = 20.dp, vertical = 40.dp)
            .border(width = 1.dp, color = Color.White, shape = RoundedCornerShape(12.dp))
            .background(Color.White, shape = RoundedCornerShape(12.dp))
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 18.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterHorizontally)
    ) {
        Image(
            painter = painterResource(id = if (!isRecording) R.drawable.ic_start_recording else R.drawable.ic_stop_recording),
            contentDescription = "Record audio",
            modifier = Modifier
                .size(50.dp)
                .clickable {
                    if (!isRecording) {
                        startRecording()
                    } else {
                        stopRecording()
                    }
                }
        )
        LinearProgressIndicator(progress = recordingProgress / maxRecordLength)
    }
}
