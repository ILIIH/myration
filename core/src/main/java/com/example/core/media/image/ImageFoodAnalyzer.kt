package com.example.core.media.image

import com.example.domain.model.Product

interface ImageFoodAnalyzer {
    suspend fun getProductsFromReceiptImage(photoUri: String): List<Product>
}