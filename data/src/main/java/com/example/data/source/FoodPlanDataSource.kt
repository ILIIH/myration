package com.example.data.source

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.data.model.FoodPlanEntity

@Dao
interface FoodPlanDataSource {
    @Insert
    suspend fun addFoodPlan(foodPlan: List<FoodPlanEntity>)

    @Query("DELETE FROM food_plan WHERE date = :date")
    suspend fun deleteFoodPlanByDate(date: String)

    @Query("SELECT * FROM food_plan")
    suspend fun getAllFoodPlanByDate(): List<FoodPlanEntity>
}
