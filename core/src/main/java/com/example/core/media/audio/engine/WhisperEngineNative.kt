package com.example.core.media.audio.engine

import android.content.Context
import android.util.Log
import com.example.domain.model.MeasurementMetric
import com.example.domain.model.Product
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import java.io.File
import kotlin.coroutines.CoroutineContext
import kotlin.math.abs


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

    override suspend fun transcribeString(recordResult: String): Deferred<List<Product>>{
        return CoroutineScope(Dispatchers.IO).async {
            val lowerText = recordResult.lowercase()

            val dateMatches = Regex("""(\d{2}/\d{2}/\d{4})""").findAll(lowerText).toList()
            val qtyMatches = Regex("""(\d+(?:[\.,]\d+)?)\s*(g|gramm|kg|pcs|ml|l)\b""").findAll(lowerText).toList()
            val wordTokens = lowerText.split(Regex("""[^a-zA-Z0-9/]+""")).filter { it.isNotBlank() }

            val results = mutableListOf<Product>()

            for (dateMatch in dateMatches) {
                val date = dateMatch.value
                val dateIndex = dateMatch.range.first

                // find closest quantity before the date
                val qty = qtyMatches.minByOrNull { abs(it.range.first - dateIndex) }
                val qtyValue = qty?.groupValues?.get(1)?.replace(",", ".")?.toFloatOrNull() ?: continue
                val metric = qty.groupValues[2]

                // find potential product name before quantity or date
                val productNameStart = maxOf(0, minOf(qty.range.first, dateIndex) - 30)
                val nameSub = lowerText.substring(productNameStart, minOf(lowerText.length, dateIndex))
                val name = Regex("""([a-z]{2,}(?:\s+[a-z]{2,}){0,2})""").find(nameSub)?.value?.trim() ?: "unknown"

                results.add(Product(
                    name = name,
                    quantity = qtyValue,
                    measurementMetric = MeasurementMetric.valueOf(metric),
                    expirationDate = date))
            }
            Log.i("product_loggig",results.toString())
            results
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
