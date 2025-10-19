package com.example.core.media.image

import com.example.domain.model.Product

class ImageGroceryAnalyzed : ImageFoodAnalyzer {
    override suspend fun getProductsFromReceiptImage(photoUri: String): List<Product> {
        return listOf()
    }
}