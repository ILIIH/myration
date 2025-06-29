package com.example.myration.viewModels

import androidx.glance.appwidget.updateAll
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.CalorieCounter
import com.example.domain.repository.CalorieRepository
import com.example.domain.repository.RecipeRepository
import com.example.myration.widgets.CalorieScreenWidget
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val calorieRepository: CalorieRepository
) : ViewModel() {
    private val _calorie: MutableStateFlow<CalorieCounter> = MutableStateFlow(CalorieCounter(1400f,0f))
    val calorie: StateFlow<CalorieCounter> = _calorie.asStateFlow()

    init {
        getCalorieInfo()
    }

    private fun getCalorieInfo(){
        viewModelScope.launch {
            _calorie.value = calorieRepository.getCalorieInfo()
        }
    }

    fun setNewCalories(newCalorie: Float, updateCalories: suspend () -> Unit){
        viewModelScope.launch {
            calorieRepository.setMaxCalorie(newCalorie)
            _calorie.value = CalorieCounter(maxCalorie = newCalorie, currentCalorie = _calorie.value.currentCalorie )
            updateCalories()
        }
    }
    fun addEatenProduct(productName: String, calorie: Float, updateCalories: suspend () -> Unit){
        viewModelScope.launch {
            calorieRepository.addToCurrentCalorie(calorie, productName)
            _calorie.value = CalorieCounter(
                maxCalorie =  _calorie.value.maxCalorie,
                currentCalorie = _calorie.value.currentCalorie + calorie
            )
            updateCalories()
        }
    }
}