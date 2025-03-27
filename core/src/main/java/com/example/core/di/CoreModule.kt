package com.example.core.di

import android.content.Context
import android.content.SharedPreferences
import com.example.core.util.AudioRecorder

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


}