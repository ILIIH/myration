package com.example.data.repository

import com.example.core.media.image.ImageGroceryAnalyzer
import java.net.URI
import com.example.data.model.maping.toData
import com.example.data.model.maping.toDomain
import com.example.data.source.ProductDataSource
import com.example.domain.model.MeasurementMetric
import com.example.domain.model.Product
import com.example.domain.repository.ProductsRepository
import javax.inject.Inject

class ProductsRepositoryImp @Inject constructor(
    private val dataSource: ProductDataSource,
    private val imageGroceryAnalyzer: ImageGroceryAnalyzer
) : ProductsRepository {
    override suspend fun addProduct(product: Product) {
        dataSource.addProduct(product.toData())
    }
    override suspend fun removeProductById(id: Int) {
        dataSource.deleteProductById(id)
    }
    override suspend fun updateProduct(product: Product){
        dataSource.updateProduct(product.toData())
    }
    override suspend fun getAllProduct(): List<Product> {
        return dataSource.getAllProduct().map { it.toDomain() }
    }
    override suspend fun getProductById(id: Int): Product {
        return dataSource.getAllProduct().first { it.id == id }.toDomain()
    }

    override suspend fun getAllProductFromRecipe(uri: String): List<Product> {
        val textFromPhoto = imageGroceryAnalyzer.getTextFromImageUri(uri)
        return listOf()
    }

    
    val knownUnits = listOf(
        "cup", "cups", "tbsp", "tablespoon", "tsp", "teaspoon", "g", "gram", "grams", "kg", "ml", "l", "oz", "pinch", "clove", "cloves", "slice", "slices", "piece", "pieces", "can", "cans", "egg", "eggs"
    )
    fun parseLine(line: String): Product {
        val words = line.lowercase().split(" ", "-", "–", "—").map { it.trim() }.filter { it.isNotEmpty() }

        var quantity: String? = null
        var unit: String? = null
        val nameTokens = mutableListOf<String>()

        for (i in words.indices) {
            val word = words[i]

            // Check for quantity (number, decimal, fraction)
            if (quantity == null && word.matches(Regex("""\d+([\/\d\s\.]*)"""))) {
                quantity = word
            } else if (unit == null && word in knownUnits) {
                unit = word
            } else {
                nameTokens.add(word)
            }
        }

        val name = nameTokens.joinToString(" ").replace(Regex("""^of\s+"""), "")
        return Product(name = name.trim(), quantity = quantity?.toFloat()?:0f, measurementMetric = MeasurementMetric.valueOf(unit?:""), expirationDate = "")
    }
}
