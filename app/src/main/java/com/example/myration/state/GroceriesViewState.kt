package com.example.myration.state

import com.example.core.Mvi.Reducer
import com.example.domain.model.Product

data class GroceriesViewState(
    val products: List<Product>
) : Reducer.ViewState
