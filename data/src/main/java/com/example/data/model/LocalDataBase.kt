package com.example.data.model

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data.source.FoodHistoryDataSource
import com.example.data.source.ProductLocalDataSource
import com.example.data.source.RecipeDataSource

@Database(
    entities = [
        ProductEntity::class,
        FoodHistoryEntity::class,
        RecipeEntity::class,
        RecipeIngredientEntity::class
    ],
    version = 2
)
abstract class LocalDataBase : RoomDatabase() {
    abstract fun productDao(): ProductLocalDataSource
    abstract fun recipeDao(): RecipeDataSource
    abstract fun foodHistoryDao(): FoodHistoryDataSource
}
