package com.example.myration.mvi.intent

import androidx.compose.runtime.Immutable
import com.example.core.mvi.Reducer
import com.example.domain.model.CalorieCounter
import com.example.domain.model.FoodHistory

@Immutable
sealed class ProfileEvents : Reducer.ViewEvent {
    data object ProfileLoading : ProfileEvents()
    data class GetProfileSetUpStatus(val status: Boolean) : ProfileEvents()
    data class ProfileError(val errorMessage: String) : ProfileEvents()
    data class ProfileUpdateCalories(val newMaxCalories: Float) : ProfileEvents()
    data class ProfileUpdateCalorieCounter(val currentCalorie: Float, val protein: Int, val fats: Int, val carbohydrates: Int) : ProfileEvents()
    data class ProfileLoaded(val profileInfo: CalorieCounter, val foodHistory: List<FoodHistory>) : ProfileEvents()
    data object ProfileShowChangeMaxCalorieWidget : ProfileEvents()
    data object ProfileShowAddEatenProductWidget : ProfileEvents()
    data object ProfileShowSetUpWidget : ProfileEvents()
}
