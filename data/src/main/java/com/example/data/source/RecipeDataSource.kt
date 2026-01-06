package com.example.data.source

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.model.RecipeEntity
import com.example.data.model.RecipeIngredientEntity

@Dao
interface RecipeDataSource {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addRecipe(recipeEntity: RecipeEntity)

    @Query(
        "SELECT r.*\n" +
            "FROM recipe r\n" +
            "WHERE r.id IN (\n" +
            "    SELECT ri.recipeID\n" +
            "    FROM recipe_ingredients ri\n" +
            "    LEFT JOIN products p ON ri.productName = p.name\n" +
            "    GROUP BY ri.recipeID\n" +
            "    HAVING COUNT(ri.productName) - COUNT(p.name) <= 3\n" +
            ");\n"
    )
    suspend fun getAllAvailableRecipes(): List<RecipeEntity>

    @Query("SELECT * FROM recipe")
    suspend fun getAllRecipes(): List<RecipeEntity>

    @Query("SELECT * FROM recipe WHERE  id = :recipeID")
    suspend fun getRecipeById(recipeID: Int): RecipeEntity

    @Insert
    suspend fun addRecipeIngredient(ingredient: RecipeIngredientEntity)

    @Query("SELECT * FROM recipe_ingredients WHERE  recipeID = :recipeID")
    suspend fun getIngredients(recipeID: Int): List<RecipeIngredientEntity>

    @Query("SELECT * FROM recipe_ingredients")
    suspend fun getAllIngredients(): List<RecipeIngredientEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllRecipe(data: List<RecipeEntity>)
}
