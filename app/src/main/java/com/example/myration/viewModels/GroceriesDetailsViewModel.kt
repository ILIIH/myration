package com.example.myration.viewModels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.core.mvi.BaseViewModel
import com.example.core.mvi.ResultState
import com.example.domain.model.MeasurementMetric
import com.example.domain.model.Product
import com.example.domain.repository.ProductsRepository
import com.example.domain.repository.RecipeRepository
import com.example.myration.mvi.effects.ProductDetailsEffect
import com.example.myration.mvi.intent.ProductDetailsEvents
import com.example.myration.mvi.reducer.ProductDetailsReducer
import com.example.myration.mvi.state.ProductDetailViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GroceriesDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val productRepository: ProductsRepository,
    private val recipeRepository: RecipeRepository
) : BaseViewModel<ResultState<ProductDetailViewState>, ProductDetailsEvents, ProductDetailsEffect>(
    initialState = ResultState.Loading,
    reducer = ProductDetailsReducer()
) {

    private val productId: Int? = savedStateHandle["productId"]

    init {
        viewModelScope.launch {
            try {
                val product = productRepository.getProductById(productId ?: 0)
                val recipes = recipeRepository.getRecipesForProduct(product)

                sendEvent(
                    ProductDetailsEvents.ProductLoaded(
                        ProductDetailViewState(
                            product = product,
                            recipes = recipes
                        )
                    )
                )
            } catch (e: Exception) {
                sendEvent(ProductDetailsEvents.ProductDetailsError(e.message ?: String()))
            }
        }
    }

    fun navigateToDish(id: Int) {
        sendEvent(ProductDetailsEvents.OnReceiptClicked(id))
    }

    fun deleteProduct() {
        viewModelScope.launch {
            try {
                if (productId != null) {
                    productRepository.removeProductById(productId)
                    sendEvent(ProductDetailsEvents.ProductDeleted)
                }
            } catch (e: Exception) {
                sendEvent(ProductDetailsEvents.ProductDetailsError(e.message ?: String()))
            }
        }
    }
    fun editProduct(
        newProductWeight: String,
        newProductName: String,
        newProductMeasurementMetric: String,
        newProductExpiration: String
    ) {
        viewModelScope.launch {
            try {
                val newProduct = Product(
                    id = productId,
                    quantity = newProductWeight.toFloat(),
                    name = newProductName,
                    measurementMetric = MeasurementMetric.fromDesc(newProductMeasurementMetric),
                    expirationDate = newProductExpiration,
                    active = true
                )
                productRepository.updateProduct(newProduct)
                sendEvent(ProductDetailsEvents.ProductUpdated(newProduct))
            } catch (e: Exception) {
                sendEvent(ProductDetailsEvents.ProductDetailsError(e.message ?: String()))
            }
        }
    }
}
