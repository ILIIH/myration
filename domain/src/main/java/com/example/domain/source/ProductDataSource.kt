package com.example.domain.source

import com.example.domain.model.Product

interface ProductDataSource {
    fun getAllProducts(): List<Product>
    fun removeProductById(id: Int)
    fun updateProductById(product: Product)
    fun addProduct(product: Product)
}
