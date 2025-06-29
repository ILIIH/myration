package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "food_history")
data class FoodHistoryEntity(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    val productName: String,
    val productCalorie: Float,
    val date: String
)
