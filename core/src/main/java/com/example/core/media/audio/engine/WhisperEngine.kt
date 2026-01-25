package com.example.core.media.audio.engine

import com.example.domain.model.Product
import kotlinx.coroutines.Deferred
import java.io.IOException

interface WhisperEngine {
    val isInitialized: Boolean

    @Throws(IOException::class)
    fun initialize(modelPath: String?, vocabPath: String?, multilingual: Boolean): Boolean
    fun deinitialize()
    suspend fun transcribeFile(wavePath: String?): Deferred<String?>
    suspend fun transcribeString(recordResult: String): Deferred<List<Product>>
    fun transcribeBuffer(samples: FloatArray?): String?
}
