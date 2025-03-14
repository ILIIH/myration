package com.example.domain.model

import java.sql.Time

enum class CookingDifficulty(val difficultyLevel: Int) {
    EASY(1),
    MIDDLE(2),
    HARD(3),
    EXTRA_HARD(4);

    companion object {
        fun fromInt(value: Int): CookingDifficulty {
            return entries.find { it.difficultyLevel == value } ?: EASY  // Default to EASY
        }
    }
}
enum class RecipeType(val desc: String) {
    MAIN("main"),
    STARTER("starter"),
    DESERT("desert"),
    COCKTAIL("cocktail"),
    SOUP("soup"),
    HIGH_PROTEIN("high protein");

    companion object {
        fun fromString(value: String): RecipeType {
            return entries.find { it.desc.equals(value, ignoreCase = true) } ?: MAIN  // Default to MAIN
        }
    }
}

data class Recipe (
    val id: Int? ,
    val name: String,
    val cookingTime: Time,
    val cookingDifficulty: CookingDifficulty,
    val kcal: Int,
    val type: RecipeType,
    val recipeCountry: String,
    val description: String)

data class RecipeIngredient (
    val id: Int,
    val recipeID: Int,
    val productName: String,
    val productAmount: String)