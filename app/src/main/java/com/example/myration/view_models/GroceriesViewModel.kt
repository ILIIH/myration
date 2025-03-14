package com.example.myration.view_models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.repository.ProductsRepositoryImp
import com.example.domain.model.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GroceriesViewModel @Inject constructor(
    private val repository: ProductsRepositoryImp
) : ViewModel() {

    private val _products: MutableStateFlow<List<Product>> = MutableStateFlow(emptyList())
    val productList: StateFlow<List<Product>> = _products.asStateFlow()

    init{
        getAllProduct()
    }

    private fun getAllProduct( ) {
        viewModelScope.launch {
            _products.value = repository.getAllProduct()
        }
    }

    fun removeProduct(id: Int?) {
        viewModelScope.launch {
            if(id!=null){
                repository.removeProductById(id)
                _products.value = _products.value.filter { it.id != id }
            }
        }
    }
}