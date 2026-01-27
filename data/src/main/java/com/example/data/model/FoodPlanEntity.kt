package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "food_plan")
data class FoodPlanEntity(
    @PrimaryKey val id: Int,
    val mealName: String,
    val mealCalorie: Double,
    val completed: Boolean,
    val completionTime: String,
    val date: String,
    val mealNumber: Int
)
