package com.example.myration.intent

import androidx.compose.runtime.Immutable
import com.example.core.mvi.Reducer
import com.example.domain.model.Product

@Immutable
sealed class Events : Reducer.ViewEvent {
    data class UpdateGroceriesLoading(val isLoading: Boolean) : Events()
    data class UpdateGroceries(val topics: List<Product>) : Events()
    data class UpdateGroceriesIsDeleted(val productId: Int) : Events()
}
