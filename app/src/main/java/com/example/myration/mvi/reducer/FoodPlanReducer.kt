package com.example.myration.mvi.reducer

import com.example.core.mvi.Reducer
import com.example.myration.mvi.effects.ManageFoodPlanEffect
import com.example.myration.mvi.intent.ManageFoodPlanEvents
import com.example.myration.mvi.state.ManageFoodPlanViewState

class FoodPlanReducer : Reducer<ManageFoodPlanViewState, ManageFoodPlanEvents, ManageFoodPlanEffect> {
    override fun reduce(
        previousState: ManageFoodPlanViewState,
        event: ManageFoodPlanEvents
    ): Pair<ManageFoodPlanViewState, ManageFoodPlanEffect?> {
        return when (event) {
            is ManageFoodPlanEvents.FoodPlanLoading -> {
                ManageFoodPlanViewState.Loading to null
            }
            is ManageFoodPlanEvents.FoodPlanCreateError -> {
                ManageFoodPlanViewState.Error(event.errorMessage) to null
            }
            is ManageFoodPlanEvents.FoodPlanCreated -> {
                ManageFoodPlanViewState.Idle to ManageFoodPlanEffect.FoodPlanApproveEffect(foodPlan = event.foodPlan)
            }
            is ManageFoodPlanEvents.FoodPlanApproved -> {
                ManageFoodPlanViewState.Idle to ManageFoodPlanEffect.NavigateToFoodPlan
            }
        }
    }
}
