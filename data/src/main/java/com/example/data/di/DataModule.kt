package com.example.data.di

import com.example.data.model.ProductDB
import com.example.data.repository.ProductsRepository
import com.example.data.source.ProductDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DataModule {
    @Provides
    fun provideProductRepository(dataSource: ProductDataSource): ProductsRepository {
        return ProductsRepository(dataSource)
    }
}