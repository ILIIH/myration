package com.example.core.media.audio.engine

import kotlinx.coroutines.Deferred
import java.io.IOException


interface WhisperEngine {
    val isInitialized: Boolean

    @Throws(IOException::class)
    fun initialize(modelPath: String?, vocabPath: String?, multilingual: Boolean): Boolean
    fun deinitialize()
    suspend fun transcribeFile(wavePath: String?): Deferred<String?>
    fun transcribeBuffer(samples: FloatArray?): String?
}