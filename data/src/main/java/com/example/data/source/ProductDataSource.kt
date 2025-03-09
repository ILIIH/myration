package com.example.data.source

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.data.model.ProductEntity

@Dao
interface ProductDataSource {
    @Insert
    suspend fun addProduct(productEntity: ProductEntity)

    @Delete
    suspend fun deleteProduct(productEntity: ProductEntity)

    @Query("SELECT * FROM products")
    suspend fun getAllProduct(): List<ProductEntity>
}