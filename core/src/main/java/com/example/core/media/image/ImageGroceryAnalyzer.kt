package com.example.core.media.image

import ai.onnxruntime.OnnxTensor
import ai.onnxruntime.OrtEnvironment
import ai.onnxruntime.OrtSession
import android.content.Context
import android.graphics.Rect
import android.net.Uri
import android.util.Log
import com.example.core.GPT.CoreTokenizer
import com.example.core.GPT.padAndFlattenBBoxes
import com.example.core.GPT.padToLength
import com.example.domain.repository.TokenizationRepository
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import kotlinx.coroutines.tasks.await
import java.io.File
import javax.inject.Inject

data class TokenWithBox(val tokenId: Int, val bbox: List<Int>)
class ImageGroceryAnalyzer @Inject constructor(
    private val context: Context,
    private val bitmapProvider: BitmapProvider,
    private val tokenizationRepository: TokenizationRepository
) {

    private val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
    val modelFile = File(context.cacheDir, "layout_ml.onnx")
    suspend fun getTextFromImageUri(photoUri: String): String? {
        return try {
            val imageWidth = 600
            val imageHeight = 700

            val bitmap = bitmapProvider.getBitmapFromUri(Uri.parse(photoUri), imageWidth, imageHeight)
            val image = bitmap?.let { InputImage.fromBitmap(it, 0) }

            val result = image?.let { recognizer.process(it).await() }
            val resultText = result?.text

            val boxes = mutableListOf<List<Int>>()
            val words = mutableListOf<String>()
            val builder = StringBuilder()

            result?.textBlocks?.forEach { block ->
                block.lines.forEach { line ->
                    line.elements.forEach { element ->
                        builder.append(" ${element.text} ")
                        words.add(element.text)
                        boxes.add(normalizeBoundingBox(element.boundingBox!!, bitmap.width, bitmap.height))
                    }
                }
            }

            analyzeText(words, builder.toString(), boxes)

            Log.d("OCR", "Detected Text: $resultText")
            resultText
        } catch (e: Exception) {
            Log.e("OCR", "Error: ${e.message}")
            null
        }
    }

    suspend fun analyzeText(words:List<String>, text: String, inputBBoxes: List<List<Int>>) {
        if (!modelFile.exists()) {
            copyAssetToFile(context, "layout_ml.onnx", modelFile)
        }

        val env = OrtEnvironment.getEnvironment()
        val session = env.createSession(modelFile.absolutePath)

        val tokenizedWord = tokenizationRepository.tokenize(words, inputBBoxes)

        val inputMap = mapOf(
            "input_ids" to OnnxTensor.createTensor(env, arrayOf(tokenizedWord.input_ids)),
            "attention_mask" to OnnxTensor.createTensor(env, arrayOf(tokenizedWord.attention_mask)),
            "token_type_ids" to OnnxTensor.createTensor(env, arrayOf(tokenizedWord.token_type_ids)),
            "bbox" to OnnxTensor.createTensor(env, arrayOf(tokenizedWord.bbox.toTypedArray())) // Already 2D, probably fine
        )

        val output = session.run(inputMap)
        val logits3D = output[0].value as Array<Array<FloatArray>> // [1][150][7]
        val logits = logits3D[0] // [150][7]

        // Convert logits to predicted labels
        val predictedLabels = logits.mapIndexed { index, tokenLogits ->
            if (tokenizedWord.attention_mask[index] == 1L) {
                tokenLogits.indices.maxByOrNull { tokenLogits[it] } ?: 0
            } else {
                -100 // Common ignore index
            }
        }


        // Use a label map to get "B-FOOD", "B-PRICE", etc.
        val labelMap = listOf(
            "O",           // 0 = Outside any entity
            "B-FOOD",      // 1 = Beginning of food item
            "I-FOOD",      // 2 = Inside a food item name
            "B-PRICE",     // 3 = Beginning of price
            "I-PRICE",     // 4 = Inside a price
            "B-QUANTITY",  // 5 = Beginning of quantity
            "I-QUANTITY"   // 6 = Inside quantity
        )

        // Group tokens into (food, price) pairs based on labels
        val pairs = mutableListOf<Pair<String, String>>()
        var currentFood = ""
        var currentPrice = ""

        val limit = minOf(predictedLabels.size, text.length)
        for (i in 0 until limit) {
            val label = labelMap[predictedLabels[i]]
            val word = words[i]

            when {
                label.startsWith("B-FOOD") -> {
                    if (currentFood.isNotEmpty() && currentPrice.isNotEmpty()) {
                        pairs.add(currentFood to currentPrice)
                    }
                    currentFood = word
                    currentPrice = ""
                }
                label.startsWith("I-FOOD") -> currentFood += " $word"
                label.startsWith("B-PRICE") -> currentPrice = word
                label.startsWith("I-PRICE") -> currentPrice += " $word"
            }
        }

        if (currentFood.isNotEmpty() && currentPrice.isNotEmpty()) {
            pairs.add(currentFood to currentPrice)
        }
        Log.d("image_scanning", "Extracted pairs: $pairs")
    }

    fun loadTokenizerFromAssets(): CoreTokenizer {
        val tokenizerStream = context.assets.open("tokenizer.json")
        val tokenizerJson = tokenizerStream.bufferedReader().use { it.readText() }
        return CoreTokenizer.fromJson(tokenizerJson)
    }

    fun normalizeBoundingBox(rect: Rect, imageWidth: Int, imageHeight: Int): List<Int> {
        val x0 = (rect.left.toFloat() / imageWidth * 1000).toInt()
        val y0 = (rect.top.toFloat() / imageHeight * 1000).toInt()
        val x1 = (rect.right.toFloat() / imageWidth * 1000).toInt()
        val y1 = (rect.bottom.toFloat() / imageHeight * 1000).toInt()
        return listOf(x0, y0, x1, y1)
    }

    fun copyAssetToFile(context: Context, assetName: String, outputFile: File) {
        context.assets.open(assetName).use { inputStream ->
            outputFile.outputStream().use { outputStream ->
                inputStream.copyTo(outputStream)
            }
        }
    }
}