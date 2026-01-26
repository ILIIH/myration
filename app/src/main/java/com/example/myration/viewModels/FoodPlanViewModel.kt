package com.example.myration.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.FoodPlan
import com.example.domain.repository.FoodPlanRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
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

    private val _date: MutableStateFlow<String> = MutableStateFlow("")
    val date: StateFlow<String> = _date

    private val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

    init {
        val current = LocalDate.now()
        _date.value = current.format(formatter)

        viewModelScope.launch {
            val plan = repository.getFoodPlans(_date.value)
            _foodList.value = plan
        }
    }

    fun changeDate(nextDay: Boolean) {
        val currentDate = LocalDate.parse(_date.value, formatter)
        val dateLocal = if (nextDay) currentDate.plusDays(1) else currentDate.minusDays(1)
        val newDate = dateLocal.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
        _date.value = newDate
        viewModelScope.launch {
            val plan = repository.getFoodPlans(newDate)
            _foodList.value = plan
        }
    }

    fun markFoodEaten(foodPlan: FoodPlan) {
        viewModelScope.launch {
            repository.deactivateFoodPlanAndRelatedIng(foodPlan)
        }
    }
}
