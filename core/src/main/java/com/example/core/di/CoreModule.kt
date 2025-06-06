package com.example.core.di

import android.content.Context
import com.example.core.media.audio.AudioDecoder
import com.example.core.media.audio.AudioRecorder
import com.example.core.media.image.BitmapProvider
import com.example.core.media.image.ImageGroceryAnalyzer
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
    fun provideAudioRecorder(@ApplicationContext context: Context): AudioRecorder {
        return AudioRecorder(context)
    }
    @Provides
    fun provideAudioDecoder(@ApplicationContext context: Context):AudioDecoder {
        return AudioDecoder(context)
    }
    @Provides
    fun provideImageAnalyzer(@ApplicationContext context: Context, bitmapProvider: BitmapProvider, tokenizationRepository: TokenizationRepository):ImageGroceryAnalyzer {
        return ImageGroceryAnalyzer(
            context = context,
            bitmapProvider = bitmapProvider,
            tokenizationRepository = tokenizationRepository
        )
    }
    @Provides
    fun provideBitmap(@ApplicationContext context: Context):BitmapProvider {
        return BitmapProvider(context)
    }
}
