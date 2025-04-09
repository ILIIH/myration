package com.example.core.media.image

import android.content.Context
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions

class ImageGroceryAnalyzer(private val context: Context) {

    private val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)

    fun getTextFromImageUri(imageUri: Uri) {
        try {
            val inputStream = context.contentResolver.openInputStream(imageUri)
            val bitmap = BitmapFactory.decodeStream(inputStream)
            val image = InputImage.fromBitmap(bitmap, 0)

            recognizer.process(image)
                .addOnSuccessListener { visionText ->
                    val resultText = visionText.text
                    Log.d("OCR", "Detected Text: $resultText")
                }
                .addOnFailureListener { e ->
                    Log.e("OCR", "Error: ${e.message}")
                }
        } catch (e: Exception) {
            Log.e("OCR", "File error: ${e.message}")
        }
    }
}