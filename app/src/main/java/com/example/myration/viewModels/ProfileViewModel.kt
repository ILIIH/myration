package com.example.myration.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.CalorieCounter
import com.example.domain.repository.CalorieRepository
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
    private val _calorie: MutableStateFlow<CalorieCounter> = MutableStateFlow(CalorieCounter(1400f,0f, 0,0,0))
    val calorie: StateFlow<CalorieCounter> = _calorie.asStateFlow()

    init {
        getCalorieInfo()
    }

    private fun getCalorieInfo(){
        viewModelScope.launch {
            _calorie.value = calorieRepository.getCalorieInfo()
        }
    }

    fun setNewMaxCalories(
        newCalorie: Float,
        updateCalories: suspend () -> Unit
    ){
        viewModelScope.launch {
            calorieRepository.setMaxCalorie(newCalorie)
            _calorie.value = CalorieCounter(
                maxCalorie = newCalorie,
                currentCalorie = _calorie.value.currentCalorie ,
                protein = _calorie.value.protein,
                fats = _calorie.value.fats,
                carbohydrates = _calorie.value.carbohydrates
            )
            updateCalories()
        }
    }
    fun addEatenProduct(
        productName: String,
        calorie: Float,
        p: Int, f: Int, c: Int,
        updateCalories: suspend () -> Unit
    ){
        viewModelScope.launch {
            calorieRepository.addToCurrentCalorie(calorie, productName, p,f,c)
            _calorie.value = CalorieCounter(
                maxCalorie =  _calorie.value.maxCalorie,
                currentCalorie = _calorie.value.currentCalorie + calorie ,
                protein = _calorie.value.protein + p,
                fats = _calorie.value.fats + f,
                carbohydrates = _calorie.value.carbohydrates + c
            )
            updateCalories()
        }
    }
}