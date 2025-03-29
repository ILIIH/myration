package com.example.myration.ui.AddProductScreen.AddProductVoice

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.RECORD_AUDIO
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.core.util.AudioRecorder
import com.example.myration.ui.theme.SecondaryBackgroundColor

@Composable
fun AddProductVoiceScreen(recorder: AudioRecorder) {
    var recordingProgress by remember { mutableFloatStateOf(0f) }
    val context = LocalContext.current
    var isRecording by remember { mutableStateOf(false) }
    val maxRecordLength = 5000f

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
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(SecondaryBackgroundColor)
    ) {
        RecordingWidget(
            isRecording,
            recordingProgress,
            maxRecordLength,
            { recorder.startRecording(); isRecording = true },
            { recorder.stopRecording(); isRecording = false }
        )

        TextFromAudioWidget()
    }

    LaunchedEffect(isRecording) {
        TimerManager.start(
            intervalMillis = 100L,
            onTick = {
                if (recordingProgress < maxRecordLength) {
                    recordingProgress += 10f
                }
            }
        )

        if (!isRecording) {
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
