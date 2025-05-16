package com.example.myration.mvi.effects

import androidx.compose.runtime.Immutable
import com.example.core.mvi.Reducer
@Immutable
sealed class ProductDetailsEffect : Reducer.ViewEffect {
    data object NavigateToGroceriesList : ProductDetailsEffect()
    data class NavigateToRecipeDetails(val recipeId: Int) : ProductDetailsEffect()
}