package com.example.myration.mvi.effects

import androidx.compose.runtime.Immutable
import com.example.core.mvi.Reducer
import com.example.domain.model.FoodPlan

@Immutable
sealed class ManageFoodPlanEffect : Reducer.ViewEffect {
    data object NavigateToFoodList : ManageFoodPlanEffect()
    data class FoodPlanApproveEffect(val foodPlan: List<FoodPlan>) : ManageFoodPlanEffect()
    data object NavigateToFoodPlan : ManageFoodPlanEffect()
}
