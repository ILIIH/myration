package com.example.myration.view_models

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.MVI.ResultState
import com.example.domain.repository.RecipeRepository
import com.example.myration.state.RecipeDetailViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: RecipeRepository
) : ViewModel() {

    val recipeId: Int? = savedStateHandle["recipeId"]

    private val _recipeDetailsState: MutableStateFlow<ResultState<RecipeDetailViewState>> = MutableStateFlow(
        ResultState.Loading)
    val recipeDetailsState: StateFlow<ResultState<RecipeDetailViewState>> = _recipeDetailsState.asStateFlow()

    init{
        if(recipeId != null ){
            viewModelScope.launch {
                try {
                    val recipe = repository.getRecipeById(recipeId)
                    val ingredients = repository.getRecipeIngredient(recipeId)

                    _recipeDetailsState.value = ResultState.Success(
                        RecipeDetailViewState(
                            id = recipe.id,
                            name = recipe.name,
                            cookingTime = recipe.cookingTime,
                            cookingDifficulty = recipe.cookingDifficulty ,
                            kcal = recipe.kcal,
                            type = recipe.type,
                            recipeCountry = recipe.recipeCountry ,
                            description = recipe.description,
                            instructions = recipe.instructions,
                            thumbnail = recipe.thumbnail,
                            videoId = recipe.videoId,
                            ingredients = ingredients
                        )
                    )
                } catch (e: Exception) {
                    _recipeDetailsState.value = ResultState.Error("Failed to load data", e)
                }
            }
        }
    }
}