package com.example.data.di

import com.example.data.repository.ProductsRepositoryImp
import com.example.data.repository.RecipeRepositoryImp
import com.example.data.source.ProductDataSource
import com.example.data.source.RecipeDataSource
import com.example.domain.repository.ProductsRepository
import com.example.domain.repository.RecipeRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DataModule {
    @Provides
    fun provideProductRepository(dataSource: ProductDataSource): ProductsRepository {
        return ProductsRepositoryImp(dataSource)
    }

    @Provides
    fun provideRecipeRepository(dataSource: RecipeDataSource): RecipeRepository {
        return RecipeRepositoryImp(dataSource)
    }
}