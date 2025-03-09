package com.example.data.repository


import com.example.data.model.ProductEntity
import com.example.data.source.ProductDataSource
import javax.inject.Inject

class ProductsRepository @Inject constructor(private val dataSource: ProductDataSource) {
    suspend fun addProduct( product: ProductEntity) {
        dataSource.addProduct(product)
    }
}