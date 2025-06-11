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
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.theme.SecondaryBackgroundColor
import com.example.myration.viewModels.AddProductVoiceViewModel

@Composable
fun AddProductVoiceScreen(
    viewModel: AddProductVoiceViewModel = hiltViewModel()
) {
    val screenState = viewModel.state.collectAsState()
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
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(SecondaryBackgroundColor)
    ) {
        RecordingWidget(
            screenState.value.isRecording ,
            screenState.value.recordingProgress,
            viewModel.MAX_RECORD_LENGTH,
            viewModel::startRecording,
            viewModel::stopRecorder
        )
        TextFromAudioWidget(screenState.value.recordingResult)
    }

    LaunchedEffect(screenState.value.recordingProgress) {
        if (screenState.value.recordingProgress >= viewModel.MAX_RECORD_LENGTH) {
            viewModel.stopRecorder()
        }
    }
    LaunchedEffect(Unit) {
        permissionLauncher.launch(permissions)
    }
}
