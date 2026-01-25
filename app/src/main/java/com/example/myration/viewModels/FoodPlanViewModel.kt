package com.example.myration.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.FoodPlan
import com.example.domain.repository.FoodPlanRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FoodPlanViewModel @Inject constructor(
    private val repository: FoodPlanRepository
) : ViewModel() {

    private val _state: MutableStateFlow<List<FoodPlan>> = MutableStateFlow(listOf())
    val state: StateFlow<List<FoodPlan>> = _state

    init {
        viewModelScope.launch {
            val plan = repository.getFoodPlans("")
            _state.value = plan
        }
    }

    fun markFoodEaten(id: Int) {
    }
}
