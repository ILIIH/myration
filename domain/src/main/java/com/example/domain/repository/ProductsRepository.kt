package com.example.domain.repository
import androidx.paging.PagingData
import com.example.domain.model.Product
import com.example.domain.model.ScanningType
import kotlinx.coroutines.flow.Flow

interface ProductsRepository {
    suspend fun addProduct(product: Product)
    suspend fun updateProduct(product: Product)
    suspend fun removeProductById(id: Int)
    fun getAllProductsPaging(): Flow<PagingData<Product>>
    suspend fun getAllProducts(): List<Product>
    suspend fun getProductById(id: Int): Product
    suspend fun getAllProductFromPhoto(uri: String, type: ScanningType): List<Product>

}
