package com.example.myration.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.Filter
import com.example.domain.model.Recipe
import com.example.domain.repository.FiltersRepository
import com.example.domain.repository.RecipeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CookingViewModel @Inject constructor(
    private val recipeRepository: RecipeRepository,
    private val filtersRepository: FiltersRepository
) : ViewModel() {

    private var fullRecipesList: List<Recipe> = emptyList()

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
            if (it.id == id) {
                _recipes.value =  filtersRepository.getFilteredItems(
                    recipes = _recipes.value,
                    filter = it
                )
                it.copy(isApplied = true)
            }
            else it
        }.sortedByDescending { it.isApplied }
    }
    fun removeFilter(id: Int){
        var firstApplied = false
        _filters.value = _filters.value.map {
            if (it.id == id) {
                it.copy(isApplied = false)
            }
            else {
                if(it.isApplied){
                    if(!firstApplied){
                        _recipes.value =  filtersRepository.getFilteredItems(
                            recipes = fullRecipesList,
                            filter = it
                        )
                        firstApplied = true
                    }
                    else {
                        _recipes.value =  filtersRepository.getFilteredItems(
                            recipes = _recipes.value,
                            filter = it
                        )
                    }
                }
                it
            }
        }.sortedByDescending { it.isApplied }
        if (!firstApplied){
            _recipes.value = fullRecipesList
        }
    }
    private fun getAllFilters() {
        _filters.value = filtersRepository.getAllFilters()
    }

    private fun getAllRecipes() {
        viewModelScope.launch {
            fullRecipesList = recipeRepository.getAllRecipe()
            _recipes.value = fullRecipesList

        }
    }
}
