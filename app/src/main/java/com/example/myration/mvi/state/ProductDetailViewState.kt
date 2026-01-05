package com.example.myration.mvi.state

import com.example.domain.model.Product
import com.example.domain.model.Recipe

data class ProductDetailViewState(
    val product: Product,
    val recipes: List<Recipe>
)
