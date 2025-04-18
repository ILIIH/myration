package com.example.data.model

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data.source.ProductDataSource
import com.example.data.source.RecipeDataSource

@Database(entities = [ProductEntity::class, RecipeEntity::class, RecipeIngredientEntity::class], version = 1)
abstract class LocalDataBase : RoomDatabase() {
    abstract fun productDao(): ProductDataSource
    abstract fun recipeDao(): RecipeDataSource
}
