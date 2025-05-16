package com.example.domain.repository
import com.example.domain.model.Product

interface ProductsRepository {
    suspend fun addProduct(product: Product)
    suspend fun updateProduct(product: Product)
    suspend fun removeProductById(id: Int)
    suspend fun getAllProduct(): List<Product>
    suspend fun getProductById(id: Int): Product
    suspend fun getAllProductFromRecipe(uri: String): List<Product>

}
