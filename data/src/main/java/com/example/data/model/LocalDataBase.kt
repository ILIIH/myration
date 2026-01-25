package com.example.data.model

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data.source.FoodHistoryDataSource
import com.example.data.source.FoodPlanDataSource
import com.example.data.source.FoodPlanIngredientDataSource
import com.example.data.source.ProductLocalDataSource
import com.example.data.source.RecipeDataSource
@Database(
    entities = [
        ProductEntity::class,
        FoodHistoryEntity::class,
        RecipeEntity::class,
        RecipeIngredientEntity::class,
        FoodPlanEntity::class,
        ProductFoodPlanLinkEntity::class,
        FoodPlanIngredientEntity::class
    ],
    version = 9
)
abstract class LocalDataBase : RoomDatabase() {
    abstract fun productDao(): ProductLocalDataSource
    abstract fun recipeDao(): RecipeDataSource
    abstract fun foodHistoryDao(): FoodHistoryDataSource
    abstract fun foodPlanDao(): FoodPlanDataSource
    abstract fun foodPlanIngredientDao(): FoodPlanIngredientDataSource
}
