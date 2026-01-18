package com.example.domain.repository
import com.example.domain.model.FoodPlan

interface FoodPlanRepository {
    suspend fun addFoodPlan(foodPlan: List<FoodPlan>)
    suspend fun getFoodPlan(date: String): List<FoodPlan>
    suspend fun deleteFoodPlan(date: String)
    suspend fun generateFoodPlan(caloriesPerDay: Int, numberOfMeals: Int, foodPref: String): List<FoodPlan>
}
