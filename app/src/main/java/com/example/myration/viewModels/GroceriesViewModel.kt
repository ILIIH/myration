package com.example.myration.viewModels

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.filter
import com.example.core.mvi.BaseViewModel
import com.example.data.repository.ProductsRepositoryImp
import com.example.domain.model.Product
import com.example.myration.mvi.intent.GroceriesEvents
import com.example.myration.mvi.effects.GroceriesEffect
import com.example.myration.mvi.reducer.GroceriesReducer
import com.example.myration.mvi.state.GroceriesViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GroceriesViewModel @Inject constructor( private val repository: ProductsRepositoryImp)
    : BaseViewModel<GroceriesViewState, GroceriesEvents, GroceriesEffect>(
    initialState = GroceriesViewState.initial(),
    reducer = GroceriesReducer()
)  {
    private val _productList = repository.getAllProductsPaging()
        .cachedIn(viewModelScope)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = PagingData.empty()
        )
    val productList: Flow<PagingData<Product>> = _productList

    fun removeProduct(id: Int?) {
        viewModelScope.launch {
            if (id != null) {
                repository.removeProductById(id)
                _productList.value.filter { it.id != id }
            }
        }
    }
}
