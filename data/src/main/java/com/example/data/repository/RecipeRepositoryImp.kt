package com.example.data.repository

import android.content.SharedPreferences
import com.example.data.model.RecipeIngredientEntity
import com.example.data.model.maping.toData
import com.example.data.model.maping.toDomain
import com.example.data.source.RecipeApiService
import com.example.data.source.RecipeDataSource
import com.example.domain.model.Recipe
import com.example.domain.model.RecipeIngredient
import com.example.domain.repository.RecipeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RecipeRepositoryImp @Inject constructor(
    private val localDataSource: RecipeDataSource,
    private val remoteDataSource: RecipeApiService,
    private val preferences: SharedPreferences
) : RecipeRepository {

    override suspend fun addRecipe(recipe: Recipe) {
        TODO("Not yet implemented")
    }

    override suspend fun getAllRecipe(): List<Recipe> {
        if (!preferences.getBoolean(IS_DATA_FETCHED, false)) initRecipes()
        return localDataSource.getAllRecipes().map { it.toDomain() }
    }
    override suspend fun getRecipeById(recipeId: Int): Recipe {
        return localDataSource.getRecipeById(recipeId).toDomain()
    }
    override suspend fun addIngredients(recipeIngredients: RecipeIngredient) {
        TODO("Not yet implemented")
    }

    override suspend fun getRecipeIngredient(recipeId: Int): List<RecipeIngredient> {
        return localDataSource.getIngredients(recipeId).map { it.toDomain() }
    }

    companion object {
        private const val IS_DATA_FETCHED = "is_data_fetched"
    }

    private suspend fun initRecipes() {
        withContext(Dispatchers.IO) {
            for (ch in 'a'..'z') {
                val meals = remoteDataSource.getRecipeStartedWith(ch)
                if (meals.meals != null) {
                    for (recipe in meals.meals) {
                        localDataSource.addRecipe(recipe.toData())
                        for ((index, ingredient) in recipe.ingredients.withIndex()) {
                            val ingredientAmount = if (index < recipe.measures.size) recipe.measures[index] ?: "" else ""
                            localDataSource.addRecipeIngredient(
                                RecipeIngredientEntity(
                                    recipeID = recipe.id?.toInt() ?: 0,
                                    productName = ingredient ?: "",
                                    productAmount = ingredientAmount
                                )
                            )
                        }
                    }
                }
                preferences.edit().putBoolean(IS_DATA_FETCHED, true).apply()
            }
        }
    }
}
