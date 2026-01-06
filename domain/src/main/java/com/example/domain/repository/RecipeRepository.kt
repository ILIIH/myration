package com.example.domain.repository

import com.example.domain.model.Product
import com.example.domain.model.Recipe
import com.example.domain.model.RecipeIngredient

interface RecipeRepository {
    suspend fun addRecipe(recipe: Recipe)
    suspend fun getAllRecipe(): List<Recipe>
    suspend fun getRecipeById(recipeId: Int): Recipe
    suspend fun addIngredients(recipeIngredients: RecipeIngredient)
    suspend fun getRecipeIngredient(recipeId: Int): List<RecipeIngredient>
    suspend fun getRecipesForProduct(product: Product): List<Recipe>
}
