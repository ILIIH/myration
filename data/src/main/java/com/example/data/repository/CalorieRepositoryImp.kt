package com.example.data.repository

import android.content.SharedPreferences
import com.example.domain.model.CalorieCounter
import com.example.domain.repository.CalorieRepository
import javax.inject.Inject

class CalorieRepositoryImp  @Inject constructor(
    private val preferences: SharedPreferences
): CalorieRepository {

    override suspend fun setCalorie(cal: Float) {
        val editor = preferences.edit()
        editor.putFloat(CURRENT_CALORIE, cal)
        editor.apply()
    }

    override suspend fun setMaxCalorie(cal: Float) {
        val editor = preferences.edit()
        editor.putFloat(MAX_CALORIE, cal)
        editor.apply()
    }

    override suspend fun getCalorieInfo(): CalorieCounter {
        val maxCalorie = preferences.getFloat(MAX_CALORIE, DEFAULT_MAX_CALORIE)
        val currentCalorie = preferences.getFloat(CURRENT_CALORIE, DEFAULT_CURRENT_CALORIE)

        return CalorieCounter(
            maxCalorie = maxCalorie,
            currentCalorie = currentCalorie
        )
    }

    override suspend fun addToCurrentCalorie(cal: Float) {
        val currentCalorie = preferences.getFloat(CURRENT_CALORIE, DEFAULT_CURRENT_CALORIE)
        val editor = preferences.edit()
        editor.putFloat(CURRENT_CALORIE, currentCalorie + cal)
        editor.apply()
    }

    companion object {
        private const val MAX_CALORIE = "max_cal"
        private const val CURRENT_CALORIE = "current_cal"

        private const val DEFAULT_MAX_CALORIE = 1400f
        private const val DEFAULT_CURRENT_CALORIE = 400f
    }
}