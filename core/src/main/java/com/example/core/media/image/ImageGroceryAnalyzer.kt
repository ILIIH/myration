package com.example.core.media.image

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import kotlinx.coroutines.tasks.await

class ImageGroceryAnalyzer(private val context: Context) {

    private val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)

    suspend fun getTextFromImageUri(bitmap: Bitmap): String? {
        return try {
            val image = InputImage.fromBitmap(bitmap, 0)

            val result = recognizer.process(image).await()
            val resultText = result.text
            Log.d("OCR", "Detected Text: $resultText")
            resultText
        } catch (e: Exception) {
            Log.e("OCR", "Error: ${e.message}")
            null
        }
    }
}