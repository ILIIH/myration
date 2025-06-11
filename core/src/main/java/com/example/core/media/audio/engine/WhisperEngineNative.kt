package com.example.core.media.audio.engine

import android.content.Context
import android.util.Log
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import java.io.File
import kotlin.coroutines.CoroutineContext


class WhisperEngineNative(private val mContext: Context) : WhisperEngine {

    init {
        val model = getFileFromAssets(mContext, "whisper-tiny.en.tflite")
        val vocab = getFileFromAssets(mContext, "filters_vocab_en.bin")
        initialize(model.absolutePath, vocab.absolutePath, false )
    }

    private val TAG = "WhisperEngineNative"
    private val nativePtr: Long // Native pointer to the TFLiteEngine instance

    override var isInitialized: Boolean = false
        private set

    override fun initialize(
        modelPath: String?,
        vocabPath: String?,
        multilingual: Boolean
    ): Boolean {
        val ret = loadModel(modelPath, multilingual)
        Log.d(TAG, "Model is loaded...$modelPath")

        isInitialized = true
        return true
    }

    override fun deinitialize() {
        freeModel()
    }

    override fun transcribeBuffer(samples: FloatArray?): String? {
        return transcribeBuffer(nativePtr, samples)
    }

    override suspend fun transcribeFile(waveFile: String?): Deferred<String?>{
        return CoroutineScope(Dispatchers.IO).async {
            transcribeFile(nativePtr, waveFile)
        }
    }

    private fun loadModel(modelPath: String?, isMultilingual: Boolean): Int {
        return loadModel(nativePtr, modelPath, isMultilingual)
    }

    init {
        nativePtr = createTFLiteEngine()
    }

    fun getFileFromAssets(context: Context, fileName: String): File {
        val assetManager = context.assets
        val inputStream = assetManager.open(fileName)

        val outFile = File(context.cacheDir, fileName)
        inputStream.use { input ->
            outFile.outputStream().use { output ->
                input.copyTo(output)
            }
        }

        return outFile
    }

    // Native methods
    private external fun createTFLiteEngine(): Long
    private external fun loadModel(
        nativePtr: Long,
        modelPath: String?,
        isMultilingual: Boolean
    ): Int

    private external fun freeModel(nativePtr: Long = this.nativePtr)

    private external fun transcribeBuffer(nativePtr: Long, samples: FloatArray?): String?
    private external fun transcribeFile(nativePtr: Long, waveFile: String?): String?

    companion object {
        init {
            System.loadLibrary("audioEngine")
        }
    }
}
