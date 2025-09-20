package com.example.data.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.example.data.model.LocalDataBase
import com.example.data.repository.CalorieRepositoryImp
import com.example.data.source.FoodHistoryDataSource
import com.example.domain.repository.CalorieRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TestDataModule {

    @Provides
    @Singleton
    fun provideCalorieRepository(
        preferences: SharedPreferences,
        foodHistoryDAO: FoodHistoryDataSource ): CalorieRepository {
        return CalorieRepositoryImp(preferences , foodHistoryDAO)
    }

    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences("test_prefs", Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun provideLocalDataBase(@ApplicationContext context: Context): LocalDataBase {
        return Room.inMemoryDatabaseBuilder(
            context = context,
            LocalDataBase::class.java
        ).build()
    }

    @Provides
    @Singleton
    fun provideFoodHistoryDAO(db: LocalDataBase): FoodHistoryDataSource {
        return db.foodHistoryDao()
    }
}