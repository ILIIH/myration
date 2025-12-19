package com.example.data.source

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.data.model.FoodHistoryEntity
import com.example.data.model.ProductEntity

@Dao
interface FoodHistoryDataSource {
    @Insert
    suspend fun addFoodProduct(foodProduct: FoodHistoryEntity)
    @Query("DELETE FROM food_history WHERE id = :id")
    suspend fun deleteFoodProductById(id: Int)

    @Query("SELECT * FROM food_history")
    suspend fun getAllFoodProducts(): List<FoodHistoryEntity>

    @Query("SELECT * FROM food_history LIMIT :amount")
    suspend fun getFirstFoodProducts(amount: Int): List<FoodHistoryEntity>

    @Query("SELECT * FROM food_history WHERE id = :id")
    suspend fun getFoodHistoryById(id: Int): FoodHistoryEntity
}
