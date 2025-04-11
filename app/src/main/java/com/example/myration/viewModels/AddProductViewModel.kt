package com.example.myration.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.mvi.ResultState
import com.example.data.repository.ProductsRepositoryImp
import com.example.domain.model.MeasurementMetric
import com.example.domain.model.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddProductViewModel @Inject constructor(
    private val repository: ProductsRepositoryImp
) : ViewModel() {

    private val _addProductState: MutableStateFlow<ResultState<Boolean>> = MutableStateFlow(
        ResultState.Success(false)
    )
    val addProductState: StateFlow<ResultState<Boolean>> = _addProductState.asStateFlow()

    fun addProduct(weight: Float, name: String, measurementMetric: String, expirationDate: String) {
        viewModelScope.launch {
            try {
                _addProductState.value = ResultState.Loading
                repository.addProduct(
                    Product(
                        weight = weight,
                        name = name,
                        measurementMetric = when (measurementMetric) {
                            "kg" -> MeasurementMetric.KILOGRAM
                            "lt" -> MeasurementMetric.LITERS
                            else -> MeasurementMetric.PIECE
                        },
                        expirationDate = expirationDate,
                        id = 0
                    )
                )
                _addProductState.value = ResultState.Success(true)
            } catch (error: Error) {
                _addProductState.value = ResultState.Error(error.message ?: "Unknown error")
            }
        }
    }
}
