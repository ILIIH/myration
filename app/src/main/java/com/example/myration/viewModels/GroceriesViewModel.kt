package com.example.myration.viewModels

import androidx.lifecycle.viewModelScope
import com.example.core.mvi.BaseViewModel
import com.example.data.repository.ProductsRepositoryImp
import com.example.domain.model.Product
import com.example.myration.mvi.intent.GroceriesEvents
import com.example.myration.mvi.effects.GroceriesEffect
import com.example.myration.mvi.reducer.GroceriesReducer
import com.example.myration.mvi.state.GroceriesViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GroceriesViewModel @Inject constructor(
    private val repository: ProductsRepositoryImp
) : BaseViewModel<GroceriesViewState, GroceriesEvents, GroceriesEffect>(
    initialState = GroceriesViewState.initial(),
    reducer = GroceriesReducer()
)  {

    private val _products: MutableStateFlow<List<Product>> = MutableStateFlow(emptyList())
    val productList: StateFlow<List<Product>> = _products.asStateFlow()

    init {
        getAllProduct()
    }

    private fun getAllProduct() {
        viewModelScope.launch {
            _products.value = repository.getAllProduct()
        }
    }

    fun removeProduct(id: Int?) {
        viewModelScope.launch {
            if (id != null) {
                repository.removeProductById(id)
                _products.value = _products.value.filter { it.id != id }
            }
        }
    }
}
