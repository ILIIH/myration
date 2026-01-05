package com.example.core.di

import android.content.Context
import com.example.core.media.audio.WavAudioRecorder
import com.example.core.media.audio.engine.WhisperEngine
import com.example.core.media.audio.engine.WhisperEngineNative
import com.example.core.media.image.BitmapProvider
import com.example.core.media.image.ImageAnalyzedFactory
import com.example.core.media.image.ImageFoodAnalyzer
import com.example.core.media.image.ImageGroceryAnalyzed
import com.example.core.media.image.ImageReceiptAnalyzer
import com.example.domain.repository.TokenizationRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object CoreModule {
    @Provides
    fun provideAudioRecorder(@ApplicationContext context: Context): WavAudioRecorder {
        return WavAudioRecorder(context)
    }

    @Provides
    fun provideImageReceiptAnalyzer(@ApplicationContext context: Context, bitmapProvider: BitmapProvider, tokenizationRepository: TokenizationRepository): ImageReceiptAnalyzer {
        return ImageReceiptAnalyzer(
            context = context,
            bitmapProvider = bitmapProvider,
            tokenizationRepository = tokenizationRepository
        )
    }

    @Provides
    fun provideImageGroceryAnalyzed(): ImageGroceryAnalyzed {
        return ImageGroceryAnalyzed()
    }

    @Provides
    fun provideImageAnalyzedFactory(imageGroceryAnalyzed: ImageGroceryAnalyzed, imageFoodAnalyzer: ImageFoodAnalyzer): ImageAnalyzedFactory {
        return ImageAnalyzedFactory(imageGroceryAnalyzed, imageFoodAnalyzer)
    }

    @Provides
    fun provideImageFoodAnalyzer(): ImageFoodAnalyzer {
        return ImageGroceryAnalyzed()
    }

    @Provides
    fun provideBitmap(@ApplicationContext context: Context): BitmapProvider {
        return BitmapProvider(context)
    }

    @Provides
    fun provideWhisperEngine(@ApplicationContext context: Context): WhisperEngine {
        return WhisperEngineNative(context)
    }
}
