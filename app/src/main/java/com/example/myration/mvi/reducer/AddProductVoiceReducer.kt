package com.example.myration.mvi.reducer

import com.example.core.mvi.Reducer
import com.example.domain.model.Product
import com.example.myration.mvi.effects.AddProductVoiceEffect
import com.example.myration.mvi.intent.AddProductVoiceEvents
import com.example.myration.mvi.state.AddProductVoiceViewState

class AddProductVoiceReducer : Reducer<AddProductVoiceViewState, AddProductVoiceEvents, AddProductVoiceEffect> {
    override fun reduce(
        previousState: AddProductVoiceViewState,
        event: AddProductVoiceEvents
    ): Pair<AddProductVoiceViewState, AddProductVoiceEffect?> {
        return when (event) {
            is AddProductVoiceEvents.RecordingInProgress -> {
                previousState.copy(isRecording = true) to null
            }
            is AddProductVoiceEvents.RecordingError -> {
                previousState to AddProductVoiceEffect.ShowRecordingError(event.errorMessage)
            }
            is AddProductVoiceEvents.Recorded -> {
                previousState.copy(recordingResult = event.recordResult) to null
            }
            is AddProductVoiceEvents.VoiceParsingError -> {
                previousState to AddProductVoiceEffect.VoiceParsingError(event.error)
            }
            is AddProductVoiceEvents.VoiceParsingResult -> {
                previousState.copy(productList = event.result) to null
            }
            is AddProductVoiceEvents.StopRecording -> {
                previousState.copy(recordingProgress = 0f, isRecording = false) to null
            }
            is AddProductVoiceEvents.RecordingProgressUpdate -> {
                previousState.copy(recordingProgress = previousState.recordingProgress + event.progressToAdd) to null
            }
            is AddProductVoiceEvents.RemoveProduct -> {
                val newProductList: List<Product> = previousState.productList.filter { it.id != event.id }
                previousState.copy(productList = newProductList) to null
            }
            is AddProductVoiceEvents.EditProduct -> {
                val newProductList: List<Product> = previousState.productList.filter { it.id != event.product.id } + event.product
                previousState.copy(productList = newProductList) to null
            }
            is AddProductVoiceEvents.ProductsSubmitted -> {
                AddProductVoiceViewState.initial() to AddProductVoiceEffect.ShowProductListSubmittedToast
            }
        }
    }
}
