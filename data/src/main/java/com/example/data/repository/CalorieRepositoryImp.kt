package com.example.data.repository

import android.content.SharedPreferences
import com.example.data.model.FoodHistoryEntity
import com.example.data.source.FoodHistoryDataSource
import com.example.domain.model.CalorieCounter
import com.example.domain.repository.CalorieRepository
import javax.inject.Inject
import androidx.core.content.edit
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class CalorieRepositoryImp  @Inject constructor(
    private val preferences: SharedPreferences,
    private val foodHistoryDAO: FoodHistoryDataSource
): CalorieRepository {

    override fun resetCalorie() {
        val prevCalDate = preferences.getString(CALORIE_DATE, "")
        if(prevCalDate != SimpleDateFormat(DATE_FORMAT, Locale.getDefault()).format(Date())){
            preferences.edit {
                putFloat(CURRENT_CALORIE, 0f)
            }
        }
    }

    override suspend fun setCalorie(cal: Float) {
        preferences.edit {
            putFloat(CURRENT_CALORIE, cal)
            putString(CALORIE_DATE, SimpleDateFormat(DATE_FORMAT, Locale.getDefault()).format(Date()))
        }
    }

    override suspend fun setMaxCalorie(cal: Float) {
        preferences.edit {
            putFloat(MAX_CALORIE, cal)
        }
    }

    override suspend fun getCalorieInfo(): CalorieCounter {
        val maxCalorie = preferences.getFloat(MAX_CALORIE, DEFAULT_MAX_CALORIE)
        val currentCalorie = preferences.getFloat(CURRENT_CALORIE, DEFAULT_CURRENT_CALORIE)
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

    override suspend fun addToCurrentCalorie(cal: Float, productName: String,p:Int,f:Int,c:Int ) {
        val currentCalorie = preferences.getFloat(CURRENT_CALORIE, DEFAULT_CURRENT_CALORIE)
        val currentP = preferences.getInt(CURRENT_PROTEIN, DEFAULT_PFC)
        val currentF = preferences.getInt(CURRENT_FATS, DEFAULT_PFC)
        val currentC = preferences.getInt(CURRENT_CARB, DEFAULT_PFC)

        foodHistoryDAO.addFoodProduct(
            FoodHistoryEntity(
                productCalorie = cal,
                productName = productName,
                date = SimpleDateFormat(DATE_FORMAT, Locale.getDefault()).format(Date())
            )
        )
        preferences.edit {
            putFloat(CURRENT_CALORIE, currentCalorie + cal)
            putInt(CURRENT_PROTEIN, currentP + p)
            putInt(CURRENT_FATS, currentF + f)
            putInt(CURRENT_CARB, currentC + c)
            putString(CALORIE_DATE, SimpleDateFormat(DATE_FORMAT, Locale.getDefault()).format(Date()))
        }
    }

    companion object {
        const val MAX_CALORIE = "max_cal"
        const val CURRENT_CALORIE = "current_cal"
        const val CURRENT_PROTEIN = "current_prot"
        const val CURRENT_FATS = "current_fats"
        const val CURRENT_CARB = "current_cards"

        const val CALORIE_DATE = "current_date"
        const val DATE_FORMAT = "yyyy-MM-dd"
        const val PREF_NAME = "ration_prefs"

        const val DEFAULT_PFC = 0
        const val DEFAULT_MAX_CALORIE = 1400f
        const val DEFAULT_CURRENT_CALORIE = 400f
    }
}