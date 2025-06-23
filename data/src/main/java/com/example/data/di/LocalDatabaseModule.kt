package com.example.data.di

import android.content.Context
import androidx.room.Room
import com.example.data.model.LocalDataBase
import com.example.data.source.ProductLocalDataSource
import com.example.data.source.RecipeDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalDatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): LocalDataBase {
        return Room.databaseBuilder(
            context,
            LocalDataBase::class.java,
            "database"
        ).build()
    }

    @Provides
    fun provideProductsDao(database: LocalDataBase): ProductLocalDataSource {
        return database.productDao()
    }

    @Provides
    fun provideRecipeDao(database: LocalDataBase, @ApplicationContext context: Context): RecipeDataSource {
        return database.recipeDao()
    }
}
