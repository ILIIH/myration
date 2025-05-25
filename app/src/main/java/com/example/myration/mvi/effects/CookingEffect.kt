package com.example.myration.mvi.effects

import androidx.compose.runtime.Immutable
import com.example.core.mvi.Reducer
@Immutable
sealed class CookingEffect : Reducer.ViewEffect {
    data class NavigateToRecipeDetails(val recipeId: Int) : CookingEffect()
}