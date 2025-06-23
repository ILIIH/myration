package com.example.data.source

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.data.model.ProductEntity

@Dao
interface ProductLocalDataSource {
    @Update
    suspend fun updateProduct(productEntity: ProductEntity)
    @Insert
    suspend fun addProduct(productEntity: ProductEntity)

    @Query("DELETE FROM products WHERE id = :id")
    suspend fun deleteProductById(id: Int)

    @Query("SELECT * FROM products")
    fun getAllProduct(): PagingSource<Int, ProductEntity>

    @Query("SELECT * FROM products WHERE id = :id")
    suspend fun getProductById(id: Int): ProductEntity
}
