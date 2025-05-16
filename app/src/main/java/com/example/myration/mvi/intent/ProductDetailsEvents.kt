package com.example.myration.mvi.intent

import androidx.compose.runtime.Immutable
import com.example.core.mvi.Reducer
import com.example.domain.model.Product
import com.example.myration.mvi.state.ProductDetailViewState

@Immutable
sealed class ProductDetailsEvents : Reducer.ViewEvent {
    data object ProductDetailsLoading : ProductDetailsEvents()
    data class ProductDetailsError(val errorMessage: String) : ProductDetailsEvents()
    data class ProductUpdated(val product: Product) : ProductDetailsEvents()
    data object ProductDeleted : ProductDetailsEvents()
    data class OnReceiptClicked(val receiptId: Int) : ProductDetailsEvents()
    data class ProductLoaded(val product: ProductDetailViewState) : ProductDetailsEvents()
}
