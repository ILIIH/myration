package com.example.domain.model

import java.awt.Color
import java.time.Duration

enum class CookingDifficulty(val difficultyLevel: Int) {
    EASY(1),
    EASY_MIDDLE(2),
    MIDDLE(3),
    EASY_HARD(4),
    HARD(5),
    EXTRA_HARD(6);

    companion object {
        fun fromInt(value: Int): CookingDifficulty {
            return entries.find { it.difficultyLevel == value } ?: EASY
        }
    }
}
enum class RecipeType(val desc: String) {
    MAIN("Main"),
    STARTER("Starter"),
    DESERT("Dessert"),
    COCKTAIL("Cocktail"),
    SOUP("Soup"),
    OTHER("Other"),
    HIGH_PROTEIN("High protein");
    companion object {
        fun fromString(value: String): RecipeType {
            return entries.find { it.desc.equals(value, ignoreCase = true) } ?: MAIN // Default to MAIN
        }
    }
}

data class Recipe(
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
    val videoId: String
)

data class RecipeIngredient(
    val id: Int,
    val recipeID: Int,
    val productName: String,
    val productAmount: String
)