package com.example.domain.model

data class CalorieCounter(
    val maxCalorie: Float,
    val currentCalorie: Float,
    val protein: Int,
    val fats: Int,
    val carbohydrates: Int
)
