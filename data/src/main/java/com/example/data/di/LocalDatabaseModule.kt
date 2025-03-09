package com.example.data.di

import android.content.Context
import androidx.room.Room
import com.example.data.model.ProductDB
import com.example.data.source.ProductDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object LocalDatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): ProductDB {
        return Room.databaseBuilder(
            context,
            ProductDB::class.java,
            "product_database"
        ).build()
    }

    @Provides
    fun provideNoteDao(database: ProductDB): ProductDataSource {
        return database.productDao()
    }
}