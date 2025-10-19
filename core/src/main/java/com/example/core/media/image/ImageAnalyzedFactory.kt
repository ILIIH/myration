package com.example.core.media.image

import com.example.domain.model.ScanningType
import javax.inject.Inject

class ImageAnalyzedFactory (
    private val imageGroceryAnalyzed: ImageGroceryAnalyzed,
    private val imageFoodAnalyzer: ImageFoodAnalyzer )
{
    fun getImageAnalyzer(type: ScanningType): ImageFoodAnalyzer {
        return when (type) {
            ScanningType.FOOD_SCANNING -> imageGroceryAnalyzed
            ScanningType.RECIPE_SCANNING -> imageFoodAnalyzer
        }
    }
}