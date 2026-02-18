package com.example.data.repository

import android.content.SharedPreferences
import androidx.core.content.edit
import com.example.data.model.FoodHistoryEntity
import com.example.data.model.maping.SDF
import com.example.data.model.maping.toDomain
import com.example.data.source.FoodHistoryDataSource
import com.example.domain.model.CalorieCounter
import com.example.domain.model.FoodHistory
import com.example.domain.model.PieChartItem
import com.example.domain.repository.CalorieRepository
import java.time.Instant
import java.time.ZoneId
import java.util.Date
import javax.inject.Inject

class CalorieRepositoryImp @Inject constructor(
    private val preferences: SharedPreferences,
    private val foodHistoryDAO: FoodHistoryDataSource
) : CalorieRepository {

    override fun resetCalorie() {
        val prevCalDate = preferences.getString(CALORIE_DATE, "")
        if (prevCalDate != SDF.format(Date())) {
            preferences.edit {
                putFloat(CURRENT_CALORIE, 0f)
                putInt(CURRENT_PROTEIN, 0)
                putInt(CURRENT_FATS, 0)
                putInt(CURRENT_CARB, 0)
            }
        }
    }

    override suspend fun setCalorie(cal: Float) {
        preferences.edit {
            putFloat(CURRENT_CALORIE, cal)
            putString(CALORIE_DATE, SDF.format(Date()))
        }
    }

    override suspend fun setMaxCalorie(cal: Float) {
        preferences.edit {
            putFloat(MAX_CALORIE, cal)
        }
    }

    override suspend fun checkMaxCalorieSetUp(): Boolean {
        return preferences.contains(MAX_CALORIE)
    }

    override suspend fun getCalorieInfo(): CalorieCounter {
        val maxCalorie = preferences.getFloat(MAX_CALORIE, DEFAULT_MAX_CALORIE)
        val isDateChanged = preferences.getString(CALORIE_DATE, "0") == SDF.format(Date())

        val currentCalorie = if (!isDateChanged) {
            preferences.getFloat(CURRENT_CALORIE, DEFAULT_CURRENT_CALORIE)
        } else {
            preferences.edit { putFloat(CURRENT_CALORIE, 0f) }
            0f
        }
        val p = preferences.getInt(CURRENT_PROTEIN, DEFAULT_PFC)
        val f = preferences.getInt(CURRENT_FATS, DEFAULT_PFC)
        val c = preferences.getInt(CURRENT_CARB, DEFAULT_PFC)

        return CalorieCounter(
            maxCalorie = maxCalorie,
            currentCalorie = currentCalorie,
            protein = p,
            fats = f,
            carbohydrates = c
        )
    }

    override suspend fun getFoodHistory(amount: Int): List<FoodHistory> {
        return foodHistoryDAO.getFirstFoodProducts(amount).map { it.toDomain() }
    }

    override suspend fun getFullFoodHistory(): List<List<FoodHistory>> {
        return foodHistoryDAO.getAllFoodProducts().map { it.toDomain() }
            .groupBy { event ->
                Instant.ofEpochMilli(event.date.time)
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate()
            }
            .toList()
            .sortedByDescending { it.first }
            .map { it.second }
    }

    override suspend fun getRationSummary(): List<PieChartItem> {
        val maxCalorie = preferences.getFloat(MAX_CALORIE, DEFAULT_MAX_CALORIE)
        val allFoodItems = foodHistoryDAO.getAllFoodProducts().map { it.toDomain() }
        val sumsByDate = allFoodItems.groupingBy { it.date }
            .fold(0f) { acc, item -> acc + item.productCalorie }

        val totalDays = sumsByDate.size
        val unsuccessfulDays = sumsByDate.values.count { it > maxCalorie }
        val successfulDays = totalDays - unsuccessfulDays

        val summary = listOf(
            PieChartItem(
                label = "$unsuccessfulDays unsuccessful days",
                amount = unsuccessfulDays,
                color = 0xFFFA3538.toInt()
            ),
            PieChartItem(
                label = "$successfulDays successful days",
                amount = successfulDays,
                color = 0xFF499F68.toInt()
            )
        )
        return summary
    }

    override suspend fun getMonthSummary(): HashMap<Int, List<PieChartItem>> {
        val maxCalorie = preferences.getFloat(MAX_CALORIE, DEFAULT_MAX_CALORIE)
        val hashMap = HashMap<Int, List<PieChartItem>>()
        foodHistoryDAO.getAllFoodProducts()
            .map { it.toDomain() }
            .groupBy { it.date.month }
            .forEach { (month, itemsInMonth) ->
                val caloriesPerDay = itemsInMonth.groupingBy { it.date }
                    .fold(0f) { acc, item -> acc + item.productCalorie }

                val unsuccessfulDays = caloriesPerDay.values.count { it > maxCalorie }
                val successfulDays = caloriesPerDay.size - unsuccessfulDays

                hashMap[month] = listOf(
                    PieChartItem(
                        label = "$unsuccessfulDays unsuccessful days",
                        amount = unsuccessfulDays,
                        color = 0xFFFA3538.toInt()
                    ),
                    PieChartItem(
                        label = "$successfulDays successful days",
                        amount = successfulDays,
                        color = 0xFF499F68.toInt()
                    )
                )
            }

        return hashMap
    }

    override suspend fun addToCurrentCalorie(cal: Float, productName: String, p: Int, f: Int, c: Int) {
        val currentCalorie = preferences.getFloat(CURRENT_CALORIE, DEFAULT_CURRENT_CALORIE)
        val currentP = preferences.getInt(CURRENT_PROTEIN, DEFAULT_PFC)
        val currentF = preferences.getInt(CURRENT_FATS, DEFAULT_PFC)
        val currentC = preferences.getInt(CURRENT_CARB, DEFAULT_PFC)

        foodHistoryDAO.addFoodProduct(
            FoodHistoryEntity(
                productCalorie = cal,
                productName = productName,
                date = SDF.format(Date())
            )
        )
        preferences.edit {
            putFloat(CURRENT_CALORIE, currentCalorie + cal)
            putInt(CURRENT_PROTEIN, currentP + p)
            putInt(CURRENT_FATS, currentF + f)
            putInt(CURRENT_CARB, currentC + c)
            putString(CALORIE_DATE, SDF.format(Date()))
        }
    }

    companion object {
        const val MAX_CALORIE = "max_cal"
        const val CURRENT_CALORIE = "current_cal"
        const val CURRENT_PROTEIN = "current_prot"
        const val CURRENT_FATS = "current_fats"
        const val CURRENT_CARB = "current_cards"

        const val CALORIE_DATE = "current_date"
        const val PREF_NAME = "ration_prefs"

        const val DEFAULT_PFC = 0
        const val DEFAULT_MAX_CALORIE = 1400f
        const val DEFAULT_CURRENT_CALORIE = 400f
    }
}
