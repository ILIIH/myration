package com.example.myration.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.CalorieCounter
import com.example.domain.model.Filter
import com.example.domain.model.Recipe
import com.example.domain.repository.CalorieRepository
import com.example.domain.repository.RecipeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

val filterList = listOf(
    "high protein",
    "easy",
    "hard",
    "fast",
    "300 kcal<",
    "500 kcal<",
    ">500 kcal"
).mapIndexed { index, name ->
    Filter(id = index, name = name, isApplied = false)
}
@HiltViewModel
class CookingViewModel @Inject constructor(
    private val repository: RecipeRepository
) : ViewModel() {

    private val _recipes: MutableStateFlow<List<Recipe>> = MutableStateFlow(emptyList())
    val recipesList: StateFlow<List<Recipe>> = _recipes.asStateFlow()

    private val _filters: MutableStateFlow<List<Filter>> = MutableStateFlow(emptyList())
    val filtersList: StateFlow<List<Filter>> = _filters.asStateFlow()

     init {
        getAllRecipes()
        getAllFilters()
    }

    fun applyFilter(id: Int){
        _filters.value = _filters.value.map {
            if (it.id == id) it.copy(isApplied = true) else it
        }.sortedByDescending { it.isApplied }
    }
    fun removeFilter(id: Int){
        _filters.value = _filters.value.map {
            if (it.id == id) it.copy(isApplied = false) else it
        }.sortedByDescending { it.isApplied }
    }
    private fun getAllFilters() {
        // TODO = change from mocked data
        _filters.value = filterList
    }

    private fun getAllRecipes() {
        viewModelScope.launch {
            _recipes.value = repository.getAllRecipe()
        }
    }
}
