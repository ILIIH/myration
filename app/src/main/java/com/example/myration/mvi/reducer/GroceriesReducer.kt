package com.example.myration.mvi.reducer

import com.example.core.mvi.Reducer
import com.example.myration.mvi.effects.GroceriesEffect
import com.example.myration.mvi.intent.GroceriesEvents
import com.example.myration.mvi.state.GroceriesViewState

class GroceriesReducer : Reducer<GroceriesViewState, GroceriesEvents, GroceriesEffect> {
    override fun reduce(
        previousState: GroceriesViewState,
        event: GroceriesEvents
    ): Pair<GroceriesViewState, GroceriesEffect?> {
        return when (event) {
            is GroceriesEvents.UpdateGroceriesIsDeleted -> {
                val updatedProducts = previousState.products.filter { it.id != event.productId }
                previousState.copy(
                    products = updatedProducts
                ) to null
            }
            is GroceriesEvents.UpdateGroceriesLoading -> {
                previousState.copy(
                    productsLoading = event.isLoading
                ) to null
            }
        }
    }
}
