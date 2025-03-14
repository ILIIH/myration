package com.example.data.repository

import com.example.data.model.maping.toDomain
import com.example.data.source.RecipeDataSource
import com.example.domain.model.Recipe
import com.example.domain.model.RecipeIngredient
import com.example.domain.repository.RecipeRepository
import javax.inject.Inject

class RecipeRepositoryImp @Inject constructor(private val dataSource: RecipeDataSource): RecipeRepository {
    override suspend fun addRecipe(recipe: Recipe) {
        TODO("Not yet implemented")
    }

    override suspend fun getAllRecipe(): List<Recipe> {
        return  dataSource.getAllRecipes().map { it.toDomain()}
    }

    override suspend fun addIngredients(recipeIngredients: RecipeIngredient) {
        TODO("Not yet implemented")
    }

    override suspend fun getRecipeIngredient(recipeId: Int): List<RecipeIngredient> {
        TODO("Not yet implemented")
    }
}