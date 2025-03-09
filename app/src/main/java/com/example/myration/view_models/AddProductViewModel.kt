package com.example.myration.view_models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.model.ProductEntity
import com.example.data.repository.ProductsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddProductViewModel @Inject constructor(
    private val repository: ProductsRepository
) : ViewModel() {

    fun addProduct( weight: Float,name: String, measurementMetric: String, expirationDate: String) {
        viewModelScope.launch {
            repository.addProduct(ProductEntity(weight = weight, name = name, measurementMetric = measurementMetric, expirationDate = expirationDate))
        }
    }
}