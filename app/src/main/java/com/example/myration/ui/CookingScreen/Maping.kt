package com.example.myration.ui.CookingScreen

import androidx.compose.ui.graphics.Color
import com.example.domain.model.CookingDifficulty
import com.example.domain.model.Recipe
import com.example.theme.NegativeBadgesColor
import com.example.theme.PositiveBadgesColor

fun Recipe.getBadgesDesc():Pair<String, Color>? {
    return when {
        kcal < 300 -> "Low Calories" to PositiveBadgesColor
        kcal > 1300 -> "High Calories" to NegativeBadgesColor
        cookingDifficulty == CookingDifficulty.EASY -> "Easy" to PositiveBadgesColor
        cookingTime.seconds < 1000 -> "Fast" to PositiveBadgesColor
        else -> null
    }
}