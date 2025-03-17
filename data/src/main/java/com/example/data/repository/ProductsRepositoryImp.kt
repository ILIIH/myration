package com.example.data.repository

import com.example.data.model.maping.toData
import com.example.data.model.maping.toDomain
import com.example.data.source.ProductDataSource
import com.example.domain.model.Product
import com.example.domain.repository.ProductsRepository
import javax.inject.Inject

class ProductsRepositoryImp @Inject constructor(private val dataSource: ProductDataSource):
    ProductsRepository {
    override suspend fun addProduct( product: Product) {
        dataSource.addProduct(product.toData())
    }
    override suspend fun removeProductById( id: Int) {
        dataSource.deleteProductById(id)
    }
    override suspend fun getAllProduct(): List<Product> {
        return dataSource.getAllProduct().map { it.toDomain() }
    }
}