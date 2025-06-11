package com.example.myration.mvi.state

import com.example.core.mvi.Reducer
import com.example.domain.model.Product

data class AddProductVoiceViewState(
    val productList: List<Product>,
    val isRecording: Boolean,
    val recordingProgress: Float,
    val recordingResult: String,
) : Reducer.ViewState {
    companion object {
        fun initial(): AddProductVoiceViewState {
            return AddProductVoiceViewState(
                productList = emptyList(),
                isRecording = false,
                recordingProgress = 0f,
                recordingResult = String()
            )
        }
    }
}

