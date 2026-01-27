package com.example.data.source

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.data.model.FoodPlanIngredientEntity

@Dao
interface FoodPlanIngredientDataSource {
    @Insert
    suspend fun addFoodPlanIngredient(ingredient: FoodPlanIngredientEntity)

    @Query("SELECT * FROM food_plan_ingredients WHERE foodPlanId = :planId")
    suspend fun getFoodPlanIngredientEntity(planId: Int): List<FoodPlanIngredientEntity>

    @Query("DELETE FROM food_plan_ingredients WHERE foodPlanId = :planId")
    suspend fun deleteFoodPlanIngredient(planId: Int)

    @Query("UPDATE food_plan_ingredients SET active = 0 WHERE foodPlanId = :planId")
    suspend fun deactivateFoodPlanIngredient(planId: Int)
}
