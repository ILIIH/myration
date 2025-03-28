package com.example.myration.ui.AddProductScreen.AddProductVoice

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.RECORD_AUDIO
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.core.util.AudioRecorder
import com.example.myration.R
import com.example.myration.ui.theme.SecondaryBackgroundColor

@Composable
fun AddProductVoiceScreen(recorder: AudioRecorder ) {
    var recordingProgress by remember { mutableFloatStateOf(0f) }
    val context = LocalContext.current

    val permissions = arrayOf(
        RECORD_AUDIO,
        WRITE_EXTERNAL_STORAGE,
        READ_EXTERNAL_STORAGE
    )

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissionsResult ->
        if (permissionsResult.any { !it.value }) {
            Toast.makeText(context, "Permissions required to record audio!", Toast.LENGTH_SHORT).show()
        }
    }

    var isRecording by remember { mutableStateOf(false) }
    val maxRecordLength = 500f

    RecordingWidget(
        isRecording,
        recordingProgress,
        maxRecordLength,
        {recorder.startRecording(); isRecording = true },
        {recorder.stopRecording(); isRecording = false})

    LaunchedEffect(isRecording) {
        TimerManager.start(
            intervalMillis = 100L,
            onTick = {
                if (recordingProgress < maxRecordLength) {
                    recordingProgress += 10f
                }
            }
        )

        if(!isRecording) {
            recordingProgress = 0f
            TimerManager.stop()
        }

    }
    LaunchedEffect(recordingProgress) {
        if (recordingProgress >= maxRecordLength) {
            isRecording = false
        }
    }



    LaunchedEffect(Unit) {
        permissionLauncher.launch(permissions)
    }
}

@Composable
fun RecordingWidget(isRecording : Boolean, recordingProgress: Float, maxRecordLength: Float, startRecording: () -> Unit, stopRecording: () -> Unit){
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
                        if(!isRecording){
                            Log.i("recording_loging", "start rerord" )
                            startRecording()
                        }
                        else {
                            Log.i("recording_loging", "stop rerord" )
                            stopRecording()
                        }
                    }
            )
            LinearProgressIndicator(progress = recordingProgress / maxRecordLength)
        }
    }
}




