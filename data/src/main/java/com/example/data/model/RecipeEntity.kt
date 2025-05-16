package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recipe")
data class RecipeEntity(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    val name: String,
    val cookingTime: String,
    val cookingDifficulty: Int,
    val kcal: Int,
    val type: String,
    val recipeCountry: String,
    val description: String,
    val instructions: String,
    val thumbnail: String,
    val youtube: String
)

@Entity(tableName = "recipe_ingredients")
data class RecipeIngredientEntity(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    val recipeID: Int,
    val productName: String,
    val productAmount: String
)
