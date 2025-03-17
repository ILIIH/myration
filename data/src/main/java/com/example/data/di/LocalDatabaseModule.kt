package com.example.data.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.data.R
import com.example.data.model.LocalDataBase
import com.example.data.model.RecipeEntity
import com.example.data.model.maping.toDomain
import com.example.data.source.ProductDataSource
import com.example.data.source.RecipeDataSource
import com.example.domain.model.CookingDifficulty
import com.example.domain.model.RecipeType
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.InputStreamReader
import java.sql.Time

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
    fun provideProductsDao(database: LocalDataBase): ProductDataSource {
        return database.productDao()
    }

    @Provides
    fun provideRecipeDao(database: LocalDataBase, @ApplicationContext context: Context): RecipeDataSource {
        return database.recipeDao()
    }

}