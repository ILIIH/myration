package com.example.myration.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.FoodPlan
import com.example.domain.repository.FoodPlanRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class FoodPlanViewModel @Inject constructor(
    private val repository: FoodPlanRepository
) : ViewModel() {

    private val _foodList: MutableStateFlow<List<FoodPlan>> = MutableStateFlow(listOf())
    val foodList: StateFlow<List<FoodPlan>> = _foodList
    private val formatter = DateTimeFormatter.ofPattern("d MMM")
    private val innerFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

    private val _date = MutableStateFlow(LocalDate.now())
    val date: StateFlow<String> = _date
        .map { it.format(formatter) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), _date.value.format(formatter))

    init {
        viewModelScope.launch {
            val plan = repository.getFoodPlans(_date.value.format(innerFormatter))
            _foodList.value = plan
        }
    }

    fun changeDate(nextDay: Boolean) {
        _date.value = if (nextDay) _date.value.plusDays(1) else _date.value.minusDays(1)
        viewModelScope.launch {
            val plan = repository.getFoodPlans(_date.value.format(innerFormatter))
            _foodList.value = plan
        }
    }

    fun markFoodEaten(foodPlan: FoodPlan) {
        viewModelScope.launch {
            repository.deactivateFoodPlanAndRelatedIng(foodPlan)
        }
    }
}
