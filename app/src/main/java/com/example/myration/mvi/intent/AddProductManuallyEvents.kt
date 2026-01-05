package com.example.myration.mvi.intent

import androidx.compose.runtime.Immutable
import com.example.core.mvi.Reducer

@Immutable
sealed class AddProductManuallyEvents : Reducer.ViewEvent {
    data class ProductAddError(val errorMessage: String) : AddProductManuallyEvents()
    data object ProductAdded : AddProductManuallyEvents()
    data object ProductLoading : AddProductManuallyEvents()
    data object ProductAdding : AddProductManuallyEvents()
}
