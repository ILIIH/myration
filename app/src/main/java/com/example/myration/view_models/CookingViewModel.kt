package com.example.myration.view_models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.repository.ProductsRepositoryImp
import com.example.domain.model.Product
import com.example.domain.model.Recipe
import com.example.domain.repository.RecipeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CookingViewModel @Inject constructor(
    private val repository: RecipeRepository
) : ViewModel() {

    private val _recipes: MutableStateFlow<List<Recipe>> = MutableStateFlow(emptyList())
    val recipesList: StateFlow<List<Recipe>> = _recipes.asStateFlow()

    init{
        getAllRecipes()
    }

    private fun getAllRecipes() {
        viewModelScope.launch {
            _recipes.value = repository.getAllRecipe()
        }
    }
}