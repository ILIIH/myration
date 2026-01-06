package com.example.domain.model

import java.util.Date

data class FoodHistory(
    val id: Int,
    val productName: String,
    val productCalorie: Float,
    val date: Date
)
