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

    @Query("UPDATE products SET active = 0 WHERE id = :prodId")
    suspend fun deactivateProductById(prodId: Int)

    @Query(
        """
    UPDATE products 
    SET 
        weight = MAX(0, weight - :amountEaten),
        active = CASE WHEN (weight - :amountEaten) <= 0 THEN 0 ELSE active END
    WHERE id = :prodId
"""
    )
    suspend fun changeProductAmount(prodId: Int, amountEaten: Float)

    @Query("SELECT * FROM products WHERE active = 1")
    fun getAllProductPaging(): PagingSource<Int, ProductEntity>

    @Query("SELECT * FROM products WHERE active = 1")
    fun getAllProduct(): List<ProductEntity>

    @Query("SELECT id FROM products WHERE name = :prodName")
    fun getProductIdByName(prodName: String): Int

    @Query("SELECT * FROM products WHERE id = :id")
    suspend fun getProductById(id: Int): ProductEntity
}
