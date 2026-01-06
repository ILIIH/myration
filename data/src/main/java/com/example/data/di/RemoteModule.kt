package com.example.data.di

import com.example.data.model.RecipeAPIEntity
import com.example.data.model.maping.MealDeserializer
import com.example.data.source.RecipeApiService
import com.example.data.source.TokenizationApi
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RemoteModule {

    private const val MEAL_BASE_URL = "https://www.themealdb.com"
    private const val TOKENIZATION_BASE_URL = "https://20ad-212-129-84-94.ngrok-free.app" // TODO: replace with actual base url

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideGson(okHttpClient: OkHttpClient): Gson {
        return GsonBuilder()
            .registerTypeAdapter(RecipeAPIEntity::class.java, MealDeserializer()) // Register custom parser
            .create()
    }

    @Provides
    @Singleton
    @Named("mealApiRetrofit")
    fun provideMealRetrofit(okHttpClient: OkHttpClient, gson: Gson): Retrofit {
        return Retrofit.Builder()
            .baseUrl(MEAL_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    @Provides
    @Singleton
    @Named("tokenizationApiRetrofit")
    fun provideHuggingFaceRetrofit(okHttpClient: OkHttpClient, gson: Gson): Retrofit {
        return Retrofit.Builder()
            .baseUrl(TOKENIZATION_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    @Provides
    @Singleton
    fun provideMealApiService(@Named("mealApiRetrofit") retrofit: Retrofit): RecipeApiService {
        return retrofit.create(RecipeApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideTokenizationService(@Named("tokenizationApiRetrofit") retrofit: Retrofit): TokenizationApi {
        return retrofit.create(TokenizationApi::class.java)
    }
}
