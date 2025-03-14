package com.example.myration.view_models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.model.ProductEntity
import com.example.data.repository.ProductsRepositoryImp
import com.example.domain.model.MeasurementMetric
import com.example.domain.model.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddProductViewModel @Inject constructor(
    private val repository: ProductsRepositoryImp
) : ViewModel() {

    fun addProduct( weight: Float,name: String, measurementMetric: String, expirationDate: String) {
        viewModelScope.launch {

            repository.addProduct(Product(weight = weight,
                name = name,
                measurementMetric = when (measurementMetric) {
                    "kg" -> MeasurementMetric.KILOGRAM
                    "lt" -> MeasurementMetric.LITERS
                    else -> MeasurementMetric.PIECE
                },
                expirationDate = expirationDate))
        }
    }
}