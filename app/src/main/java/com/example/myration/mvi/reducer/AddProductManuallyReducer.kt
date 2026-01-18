package com.example.myration.mvi.reducer

import com.example.core.mvi.Reducer
import com.example.myration.mvi.effects.AddProductManuallyEffect
import com.example.myration.mvi.intent.AddProductManuallyEvents
import com.example.myration.mvi.state.AddProductManuallyViewState

class AddProductManuallyReducer : Reducer<AddProductManuallyViewState, AddProductManuallyEvents, AddProductManuallyEffect> {
    override fun reduce(
        previousState: AddProductManuallyViewState,
        event: AddProductManuallyEvents
    ): Pair<AddProductManuallyViewState, AddProductManuallyEffect?> {
        return when (event) {
            is AddProductManuallyEvents.ProductAdded -> {
                AddProductManuallyViewState.Loaded to null
            }
            is AddProductManuallyEvents.ProductAddError -> {
                AddProductManuallyViewState.Error(event.errorMessage) to null
            }
            is AddProductManuallyEvents.ProductLoading -> {
                AddProductManuallyViewState.Loading to null
            }
            is AddProductManuallyEvents.ProductAdding -> {
                previousState to null
            }
        }
    }
}
