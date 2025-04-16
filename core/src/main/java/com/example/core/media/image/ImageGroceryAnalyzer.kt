package com.example.core.media.image

import android.net.Uri
import android.util.Log
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import kotlinx.coroutines.tasks.await

class ImageGroceryAnalyzer(private val bitmapProvider: BitmapProvider) {

    private val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)

    suspend fun getTextFromImageUri(photoUri: String): String? {
        return try {
            val bitmap = bitmapProvider.getBitmapFromUri(Uri.parse(photoUri), 600, 700)
            val image = bitmap?.let { InputImage.fromBitmap(it, 0) }

            val result = image?.let { recognizer.process(it).await() }
            val resultText = result?.text
            Log.d("OCR", "Detected Text: $resultText")
            resultText
        } catch (e: Exception) {
            Log.e("OCR", "Error: ${e.message}")
            null
        }
    }
}