package com.example.myration.mvi.intent

import androidx.compose.runtime.Immutable
import com.example.core.mvi.Reducer
import com.example.domain.model.FoodPlan

@Immutable
sealed class ManageFoodPlanEvents : Reducer.ViewEvent {
    data class FoodPlanCreateError(val errorMessage: String) : ManageFoodPlanEvents()
    data class FoodPlanCreated(val foodPlan: List<FoodPlan>) : ManageFoodPlanEvents()
    data object FoodPlanLoading : ManageFoodPlanEvents()
    data object FoodPlanApproved : ManageFoodPlanEvents()
}
