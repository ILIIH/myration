package com.example.myration.intent

import androidx.compose.runtime.Immutable
import com.example.core.Mvi.Reducer

@Immutable
sealed class Effects : Reducer.ViewEffect {
    data object NavigateToAddProduct : Effects()
    data object NavigateToMyGroceries : Effects()
    data class NavigateToRecipeDetails(val recipeId: Int) : Effects()
}
