package com.example.myration.mvi.intent

import androidx.compose.runtime.Immutable
import com.example.core.mvi.Reducer
import com.example.domain.model.Filter
import com.example.domain.model.Recipe

@Immutable
sealed class CookingEvents : Reducer.ViewEvent {
    data object CookingScreenLoading : CookingEvents()
    data class ApplyFilter(val filterId: Int) : CookingEvents()
    data class RemoveFilter(val filterId: Int) : CookingEvents()
    data class onRecipeClicked(val recipeId: Int) : CookingEvents()
    data class CookingScreenLoaded(
        val recipesList: List<Recipe>,
        val filtersList: List<Filter>,
        val fullRecipesList: List<Recipe>
    ) : CookingEvents()
}
