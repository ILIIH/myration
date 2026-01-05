package com.example.myration.mvi.effects

import androidx.compose.runtime.Immutable
import com.example.core.mvi.Reducer
@Immutable
sealed class AddProductVoiceEffect : Reducer.ViewEffect {
    data class ShowRecordingError(val error: String) : AddProductVoiceEffect()
    data object ShowProductListSubmittedToast : AddProductVoiceEffect()
    data class VoiceParsingError(val error: String) : AddProductVoiceEffect()
}
