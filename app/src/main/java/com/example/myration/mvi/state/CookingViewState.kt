package com.example.myration.mvi.state

import com.example.core.mvi.Reducer
import com.example.domain.model.Filter
import com.example.domain.model.Product
import com.example.domain.model.Recipe
import kotlinx.coroutines.flow.StateFlow

data class CookingViewState(
    val recipesList: List<Recipe>,
    val filtersList: List<Filter>,
    val fullRecipesList: List<Recipe>
) : Reducer.ViewState
