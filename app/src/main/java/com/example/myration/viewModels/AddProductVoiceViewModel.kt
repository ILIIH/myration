package com.example.myration.viewModels

import android.Manifest
import androidx.annotation.RequiresPermission
import androidx.lifecycle.viewModelScope
import com.example.core.media.audio.WavAudioRecorder
import com.example.core.media.audio.engine.WhisperEngine
import com.example.core.mvi.BaseViewModel
import com.example.myration.mvi.effects.AddProductVoiceEffect
import com.example.myration.mvi.intent.AddProductVoiceEvents
import com.example.myration.mvi.reducer.AddProductVoiceReducer
import com.example.myration.mvi.state.AddProductVoiceViewState
import com.example.myration.ui.AddProductScreen.AddProductVoice.TimerManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddProductVoiceViewModel @Inject constructor(
    private val audioRecorder: WavAudioRecorder,
    private val audioDecoder: WhisperEngine
) :  BaseViewModel<AddProductVoiceViewState, AddProductVoiceEvents, AddProductVoiceEffect>(
    initialState = AddProductVoiceViewState.initial(),
    reducer = AddProductVoiceReducer()
) {

    val MAX_RECORD_LENGTH = 5000f

    @RequiresPermission(Manifest.permission.RECORD_AUDIO)
    fun startRecording() {
        sendEvent(AddProductVoiceEvents.RecordingInProgress)
        audioRecorder.startRecording()
        TimerManager.start(
            intervalMillis = 100L,
            onTick = {
                if (state.value.recordingProgress < MAX_RECORD_LENGTH) {
                    sendEvent(AddProductVoiceEvents.RecordingProgressUpdate(10f))
                }
            }
        )
    }

    fun stopRecorder() {
        TimerManager.stop()
        sendEvent(AddProductVoiceEvents.StopRecording)
        viewModelScope.launch {
            val filePath =  audioRecorder.stopRecording()
            val result = audioDecoder.transcribeFile(filePath).await().toString()
            sendEvent(AddProductVoiceEvents.Recorded(result))
        }
    }
}