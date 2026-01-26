package com.example.data.source

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.data.model.FoodPlanEntity

@Dao
interface FoodPlanDataSource {
    @Insert
    suspend fun addFoodPlan(foodPlan: List<FoodPlanEntity>)

    @Query("DELETE FROM food_plan WHERE date = :date")
    suspend fun deleteFoodPlanByDate(date: String)

    @Update
    suspend fun updateFoodPlan(plan: FoodPlanEntity)

    @Query("SELECT * FROM food_plan WHERE date = :date")
    suspend fun getAllFoodPlanByDate(date: String): List<FoodPlanEntity>

    @Query("SELECT MAX(id) FROM food_plan")
    suspend fun getLastId(): Int?

    suspend fun getLastIdOrZero(): Int = getLastId() ?: 0
}
