package com.example.domain.repository
import androidx.paging.PagingData
import com.example.domain.model.Product
import kotlinx.coroutines.flow.Flow

interface ProductsRepository {
    suspend fun addProduct(product: Product)
    suspend fun updateProduct(product: Product)
    suspend fun removeProductById(id: Int)
    fun getAllProducts(): Flow<PagingData<Product>>
    suspend fun getProductById(id: Int): Product
    suspend fun getAllProductFromRecipe(uri: String): List<Product>

}
