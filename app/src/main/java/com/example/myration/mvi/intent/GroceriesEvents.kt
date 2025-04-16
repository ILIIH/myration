package com.example.myration.mvi.intent

import androidx.compose.runtime.Immutable
import com.example.core.mvi.Reducer
import com.example.domain.model.Product

@Immutable
sealed class GroceriesEvents : Reducer.ViewEvent {
    data class UpdateGroceriesLoading(val isLoading: Boolean) : GroceriesEvents()
    data class UpdateGroceriesIsDeleted(val productId: Int) : GroceriesEvents()
}
