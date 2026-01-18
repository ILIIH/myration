package com.example.myration.viewModels

import androidx.lifecycle.viewModelScope
import com.example.core.mvi.BaseViewModel
import com.example.domain.model.FoodPlan
import com.example.domain.repository.FoodPlanRepository
import com.example.myration.mvi.effects.ManageFoodPlanEffect
import com.example.myration.mvi.intent.ManageFoodPlanEvents
import com.example.myration.mvi.reducer.FoodPlanReducer
import com.example.myration.mvi.state.ManageFoodPlanViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ManageFoodPlanViewModel @Inject constructor(
    private val repository: FoodPlanRepository
) : BaseViewModel<ManageFoodPlanViewState, ManageFoodPlanEvents, ManageFoodPlanEffect>(
    initialState = ManageFoodPlanViewState.initial(),
    reducer = FoodPlanReducer()
) {
    fun createPlan(caloriesPerDay: Int, numberOfMeals: Int, foodPref: String) {
        sendEvent(ManageFoodPlanEvents.FoodPlanLoading)
        viewModelScope.launch {
            try {
                val foodPlan = repository.generateFoodPlan(caloriesPerDay, numberOfMeals, foodPref)
                sendEvent(ManageFoodPlanEvents.FoodPlanCreated(foodPlan = foodPlan))
            } catch (error: Exception) {
                sendEvent(ManageFoodPlanEvents.FoodPlanCreateError(errorMessage = error.message ?: "Food plan loading error"))
            }
        }
    }

    fun saveFoodPlan(foodPlan: List<FoodPlan>) {
        viewModelScope.launch {
            try {
                repository.addFoodPlan(foodPlan)
                sendEvent(ManageFoodPlanEvents.FoodPlanApproved)
            } catch (error: Exception) {
                sendEvent(ManageFoodPlanEvents.FoodPlanCreateError(errorMessage = error.message ?: "Food plan loading error"))
            }
        }
    }
}
