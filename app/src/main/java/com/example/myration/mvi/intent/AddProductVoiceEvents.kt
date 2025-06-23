package com.example.myration.mvi.intent

import androidx.compose.runtime.Immutable
import com.example.core.mvi.Reducer
import com.example.domain.model.Product

@Immutable
sealed class AddProductVoiceEvents : Reducer.ViewEvent {
    data class RecordingError(val errorMessage: String) : AddProductVoiceEvents()
    data class RecordingProgressUpdate(val progressToAdd: Float) : AddProductVoiceEvents()
    data object RecordingInProgress : AddProductVoiceEvents()
    data object StopRecording : AddProductVoiceEvents()
    data object ProductsSubmitted : AddProductVoiceEvents()
    data class Recorded(val recordResult: String) : AddProductVoiceEvents()
    data class VoiceParsingError(val error: String) : AddProductVoiceEvents()
    data class VoiceParsingResult(val result: List<Product>) : AddProductVoiceEvents()
    data class RemoveProduct(val id: Int) : AddProductVoiceEvents()
    data class EditProduct(val product: Product) : AddProductVoiceEvents()

}
