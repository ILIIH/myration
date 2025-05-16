package com.example.myration.mvi.state

import com.example.core.mvi.Reducer
import com.example.domain.model.CookingDifficulty
import com.example.domain.model.RecipeIngredient
import com.example.domain.model.RecipeType
import java.time.Duration

data class RecipeDetailViewState(
    val id: Int,
    val name: String,
    val cookingTime: Duration,
    val cookingDifficulty: CookingDifficulty,
    val kcal: Int,
    val type: RecipeType,
    val recipeCountry: String,
    val description: String,
    val instructions: String,
    val thumbnail: String,
    val videoId: String,
    val ingredients: List<RecipeIngredient>
) : Reducer.ViewState
