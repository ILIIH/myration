package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "food_plan")
data class FoodPlanEntity(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    val mealName: String,
    val mealCalorie: Float,
    val completed: Boolean,
    val completionTime: String,
    val date: String,
    val mealNumber: Int,
    val mealAmount: String
)
