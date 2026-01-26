package com.example.domain.repository
import com.example.domain.model.FoodPlan

interface FoodPlanRepository {
    suspend fun addFoodPlan(foodPlan: List<FoodPlan>)
    suspend fun getFoodPlans(date: String): List<FoodPlan>
    suspend fun deactivateFoodPlanAndRelatedIng(foodPlan: FoodPlan)
    suspend fun deleteFoodPlan(date: String)
    suspend fun generateFoodPlan(caloriesPerDay: Int, numberOfMeals: Int, foodPref: String, date: String): List<FoodPlan>
}
