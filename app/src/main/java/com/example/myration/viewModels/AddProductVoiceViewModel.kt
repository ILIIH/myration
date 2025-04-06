package com.example.myration.viewModels

import androidx.lifecycle.ViewModel
import com.example.core.util.AudioRecorder
import com.example.myration.ui.AddProductScreen.AddProductVoice.TimerManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class AddProductVoiceViewModel @Inject constructor(
    private val audioRecorder: AudioRecorder
) : ViewModel() {

    val maxRecordLength = 5000f

    private val _recordingProgress : MutableStateFlow<Float> = MutableStateFlow(0f)
    val recordingProgress : StateFlow<Float> = _recordingProgress.asStateFlow()

    private val _isRecording : MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isRecording : StateFlow<Boolean> = _isRecording.asStateFlow()

    fun startRecording() {
        audioRecorder.startRecording()
        _isRecording.value = true

        TimerManager.start(
            intervalMillis = 100L,
            onTick = {
                if (_recordingProgress.value < maxRecordLength) {
                    _recordingProgress.value += 10f
                }
            }
        )
    }

    fun stopRecorder() {
        audioRecorder.stopRecording()
        _isRecording.value = false
        _recordingProgress.value = 0f
        TimerManager.stop()
    }


}