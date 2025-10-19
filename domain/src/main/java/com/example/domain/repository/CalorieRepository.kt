package com.example.domain.repository
import com.example.domain.model.CalorieCounter
import com.example.domain.model.Product

interface CalorieRepository {
    suspend fun setCalorie(cal: Float)
    suspend fun setMaxCalorie(cal: Float)
    suspend fun getCalorieInfo(): CalorieCounter

    suspend fun checkMaxCalorieSetUp(): Boolean
    suspend fun addToCurrentCalorie(cal: Float, productName: String, p: Int, f:Int, c:Int)
    fun resetCalorie()
}
