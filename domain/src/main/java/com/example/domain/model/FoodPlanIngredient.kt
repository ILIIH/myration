package com.example.domain.model

data class FoodPlanIngredient(
    val amountGrams: Double,
    val name: String,
    val amountSource: Float,
    val calories: Float,
    val foodPlanId: Int,
    val active: Boolean,
    val productId: Int
)
