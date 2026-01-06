package com.example.myration.viewModels

import androidx.lifecycle.viewModelScope
import com.example.core.mvi.BaseViewModel
import com.example.data.repository.ProductsRepositoryImp
import com.example.domain.model.MeasurementMetric
import com.example.domain.model.Product
import com.example.myration.mvi.effects.AddProductManuallyEffect
import com.example.myration.mvi.intent.AddProductManuallyEvents
import com.example.myration.mvi.reducer.AddProductManuallyReducer
import com.example.myration.mvi.state.AddProductManuallyViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddProductViewModel @Inject constructor(
    private val repository: ProductsRepositoryImp
) : BaseViewModel<AddProductManuallyViewState, AddProductManuallyEvents, AddProductManuallyEffect>(
    initialState = AddProductManuallyViewState.initial(),
    reducer = AddProductManuallyReducer()
) {
    fun addProduct(weight: String, name: String, measurementMetric: String, expirationDate: String) {
        viewModelScope.launch {
            try {
                if (isDataInvalid(weight, name, measurementMetric, expirationDate)) {
                    throw IllegalStateException("One of the fields is empty or invalid")
                }

                sendEvent(AddProductManuallyEvents.ProductLoading)
                repository.addProduct(
                    Product(
                        quantity = weight.toFloat(),
                        name = name,
                        measurementMetric = when (measurementMetric) {
                            "kg" -> MeasurementMetric.KILOGRAM
                            "lt" -> MeasurementMetric.LITERS
                            else -> MeasurementMetric.PIECES
                        },
                        expirationDate = expirationDate
                    )
                )
                sendEvent(AddProductManuallyEvents.ProductAdded)
            } catch (error: Exception) {
                sendEvent(AddProductManuallyEvents.ProductAddError(errorMessage = error.message ?: ""))
            }
        }
    }

    fun returnToAddingStage() {
        sendEvent(AddProductManuallyEvents.ProductAdding)
    }
    private fun isDataInvalid(weight: String, name: String, measurementMetric: String, expirationDate: String): Boolean {
        return weight.isEmpty() || name.isEmpty() || measurementMetric.isEmpty() || expirationDate.isEmpty()
    }
}
