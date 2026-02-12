package com.example.domain.repository
import com.example.domain.model.CalorieCounter
import com.example.domain.model.FoodHistory
import com.example.domain.model.PieChartItem

interface CalorieRepository {
    suspend fun setCalorie(cal: Float)
    suspend fun setMaxCalorie(cal: Float)
    suspend fun getCalorieInfo(): CalorieCounter
    suspend fun getFoodHistory(amount: Int): List<FoodHistory>
    suspend fun getFullFoodHistory(): List<List<FoodHistory>>
    suspend fun getMonthSummary(): HashMap<Int, List<PieChartItem>>
    suspend fun getRationSummary(): List<PieChartItem>
    suspend fun checkMaxCalorieSetUp(): Boolean
    suspend fun addToCurrentCalorie(cal: Float, productName: String, p: Int, f: Int, c: Int)
    fun resetCalorie()
}
