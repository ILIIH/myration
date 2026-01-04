package com.example.data.di

import android.content.SharedPreferences
import com.example.core.media.image.ImageAnalyzedFactory
import com.example.core.media.image.ImageReceiptAnalyzer
import com.example.data.repository.CalorieRepositoryImp
import com.example.data.repository.FiltersRepositoryImp
import com.example.data.repository.ProductsRepositoryImp
import com.example.data.repository.RecipeRepositoryImp
import com.example.data.repository.TokenizationRepositoryImp
import com.example.data.source.FoodHistoryDataSource
import com.example.data.source.TokenizationApi
import com.example.data.source.ProductLocalDataSource
import com.example.data.source.RecipeApiService
import com.example.data.source.RecipeDataSource
import com.example.data.usecase.GetSoonExpiredProductsImp
import com.example.domain.repository.CalorieRepository
import com.example.domain.repository.FiltersRepository
import com.example.domain.repository.ProductsRepository
import com.example.domain.repository.RecipeRepository
import com.example.domain.repository.TokenizationRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DataModule {
    @Provides
    fun provideProductRepository(
        dataSource: ProductLocalDataSource,
        imageAnalyzedFactory: ImageAnalyzedFactory
    ): ProductsRepository {
        return ProductsRepositoryImp(dataSource, imageAnalyzedFactory)
    }

    @Provides
    fun provideGetSoonExpUseCase(repository: ProductsRepository): GetSoonExpiredProductsImp {
        return GetSoonExpiredProductsImp(repository)
    }
    @Provides
    fun provideTokenizationRepository(
        huggingFaceApi: TokenizationApi
    ): TokenizationRepository {
        return TokenizationRepositoryImp(huggingFaceApi)
    }

    @Provides
    fun provideRecipeRepository(
        localDataSource: RecipeDataSource,
        remoteDataSource: RecipeApiService,
        preferences: SharedPreferences
    ): RecipeRepository {
        return RecipeRepositoryImp(localDataSource, remoteDataSource, preferences)
    }

    @Provides
    fun provideCalorieRepository(preferences: SharedPreferences, dao: FoodHistoryDataSource): CalorieRepository {
        val repo: CalorieRepository = CalorieRepositoryImp(preferences, dao)
        repo.resetCalorie()
        return repo
    }

    @Provides
    fun provideFiltersRepository(): FiltersRepository {
        return FiltersRepositoryImp()
    }

}
