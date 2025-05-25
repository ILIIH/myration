package com.example.myration.mvi.reducer

import com.example.core.mvi.Reducer
import com.example.core.mvi.ResultState
import com.example.domain.model.CookingDifficulty
import com.example.domain.model.Filter
import com.example.domain.model.Recipe
import com.example.domain.model.RecipeFilter
import com.example.domain.model.RecipeType
import com.example.myration.mvi.effects.CookingEffect
import com.example.myration.mvi.intent.CookingEvents
import com.example.myration.mvi.state.CookingViewState

class CookingReducer : Reducer<ResultState<CookingViewState>, CookingEvents, CookingEffect> {
    override fun reduce(
        previousState: ResultState<CookingViewState>,
        event: CookingEvents
    ): Pair<ResultState<CookingViewState>, CookingEffect?> {
        return when(event){
            is CookingEvents.CookingScreenLoading -> {
                ResultState.Loading to null
            }
            is CookingEvents.ApplyFilter -> {
                if(previousState is ResultState.Success){
                    var newRecipesList = previousState.data.fullRecipesList
                    val newFilterList = previousState.data.filtersList.map {
                        if (it.id == event.filterId) {
                            newRecipesList =  getFilteredItems(
                                recipes = newRecipesList,
                                filter = it
                            )
                            it.copy(isApplied = true)
                        }
                        else it
                    }.sortedByDescending { it.isApplied }

                    ResultState.Success(
                        CookingViewState(
                            recipesList = newRecipesList,
                            filtersList = newFilterList,
                            fullRecipesList = previousState.data.fullRecipesList
                        )
                    ) to null
                }
                else ResultState.Error("No recipes was downloaded") to null
            }
            is CookingEvents.RemoveFilter -> {
                if(previousState is ResultState.Success){
                    var newRecipesList = listOf<Recipe>()
                    var firstApplied = false
                    val newFilters = previousState.data.filtersList.map {
                        if (it.id == event.filterId) {
                            it.copy(isApplied = false)
                        }
                        else {
                            if(it.isApplied){
                                if(!firstApplied){
                                    newRecipesList = getFilteredItems(
                                        recipes = previousState.data.fullRecipesList,
                                        filter = it
                                    )
                                    firstApplied = true
                                }
                                else {
                                    newRecipesList = getFilteredItems(
                                        recipes = newRecipesList,
                                        filter = it
                                    )
                                }
                            }
                            it
                        }
                    }.sortedByDescending { it.isApplied }
                    if (!firstApplied){
                        newRecipesList = previousState.data.fullRecipesList
                    }
                    ResultState.Success(
                        CookingViewState(
                            recipesList = newRecipesList,
                            filtersList = newFilters,
                            fullRecipesList = previousState.data.fullRecipesList
                        )
                    ) to null
                }
                else ResultState.Error("No recipes was downloaded") to null
            }
            is CookingEvents.onRecipeClicked -> {
                previousState to CookingEffect.NavigateToRecipeDetails(recipeId = event.recipeId)
            }

            is CookingEvents.CookingScreenLoaded -> {
                ResultState.Success(
                    CookingViewState(
                        recipesList = event.recipesList,
                        filtersList = event.filtersList,
                        fullRecipesList = event.fullRecipesList
                    )
                ) to null
            }
        }
    }

    // TODO("Change implementation to flow API")
    private fun getFilteredItems(recipes: List<Recipe>, filter: Filter): List<Recipe> {
        return when (filter.type) {
            RecipeFilter.HIGH_PROTEIN -> {
                recipes.filter { it.type == RecipeType.HIGH_PROTEIN }
            }
            RecipeFilter.EASY -> {
                recipes.filter { it.cookingDifficulty == CookingDifficulty.EASY }
            }
            RecipeFilter.HARD -> {
                recipes.filter { it.cookingDifficulty == CookingDifficulty.HARD } // FIXED
            }
            RecipeFilter.FAST -> {
                recipes.filter { it.cookingTime.seconds < 1000 }
            }
            RecipeFilter.LESS_THAN_300_KCAL -> {
                recipes.filter { it.kcal < 300 }
            }
            RecipeFilter.LESS_THAN_500_KCAL -> {
                recipes.filter { it.kcal < 500 }
            }
            RecipeFilter.MORE_THAN_500_KCAL -> {
                recipes.filter { it.kcal > 500 }
            }
        }
    }

}
