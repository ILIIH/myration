package com.example.myration.mvi.intent

import androidx.compose.runtime.Immutable
import com.example.core.mvi.Reducer

@Immutable
sealed class GroceriesEvents : Reducer.ViewEvent {
    data class UpdateGroceriesLoading(val isLoading: Boolean) : GroceriesEvents()
    data class UpdateGroceriesIsDeleted(val productId: Int) : GroceriesEvents()
}
