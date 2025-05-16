package com.example.myration.mvi.state

import com.example.core.mvi.Reducer
import com.example.domain.model.Product

data class GroceriesViewState(
    val productsLoading: Boolean,
    val products: List<Product>
) : Reducer.ViewState {
    companion object {
        fun initial(): GroceriesViewState {
            return GroceriesViewState(
                products = emptyList(),
                productsLoading = false
            )
        }
    }
}
