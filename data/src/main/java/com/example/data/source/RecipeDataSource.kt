package com.example.data.source

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.model.RecipeEntity
import com.example.data.model.RecipeIngredientEntity

@Dao
interface RecipeDataSource {
    @Insert
    suspend fun addRecipe(recipeEntity: RecipeEntity)

    @Query("SELECT * FROM recipe")
    suspend fun getAllRecipes(): List<RecipeEntity>

    @Insert
    suspend fun addRecipeIngredients(ingredient: RecipeIngredientEntity)

    @Query("SELECT * FROM recipe_ingredients WHERE  recipeID = :recipeID")
    suspend fun getIngredients(recipeID: Int): List<RecipeIngredientEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllRecipe(data: List<RecipeEntity>)

}