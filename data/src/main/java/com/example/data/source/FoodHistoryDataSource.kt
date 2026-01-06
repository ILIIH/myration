package com.example.data.source

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.data.model.FoodHistoryEntity
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
