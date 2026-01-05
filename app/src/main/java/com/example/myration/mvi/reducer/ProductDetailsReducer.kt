package com.example.myration.mvi.reducer

import android.util.Log
import com.example.core.mvi.Reducer
import com.example.core.mvi.ResultState
import com.example.domain.model.Recipe
import com.example.myration.mvi.effects.ProductDetailsEffect
import com.example.myration.mvi.intent.ProductDetailsEvents
import com.example.myration.mvi.state.ProductDetailViewState

class ProductDetailsReducer : Reducer<ResultState<ProductDetailViewState>, ProductDetailsEvents, ProductDetailsEffect> {
    override fun reduce(
        previousState: ResultState<ProductDetailViewState>,
        event: ProductDetailsEvents
    ): Pair<ResultState<ProductDetailViewState>, ProductDetailsEffect?> {
        Log.i("reducing_logging", "event reduced -> ${event.javaClass}")

        return when (event) {
            is ProductDetailsEvents.ProductDetailsLoading -> {
                ResultState.Loading to null
            }
            is ProductDetailsEvents.ProductDetailsError -> {
                ResultState.Error(event.errorMessage) to null
            }
            is ProductDetailsEvents.ProductLoaded -> {
                ResultState.Success(event.product) to null
            }
            is ProductDetailsEvents.OnReceiptClicked -> {
                previousState to ProductDetailsEffect.NavigateToRecipeDetails(event.receiptId)
            }
            is ProductDetailsEvents.ProductDeleted -> {
                previousState to ProductDetailsEffect.NavigateToGroceriesList
            }
            is ProductDetailsEvents.ProductUpdated -> {
                val recipes = if (previousState is ResultState.Success) previousState.data.recipes else listOf<Recipe>()
                ResultState.Success(
                    ProductDetailViewState(
                        product = event.product,
                        recipes = recipes
                    )
                ) to null
            }
        }
    }
}
