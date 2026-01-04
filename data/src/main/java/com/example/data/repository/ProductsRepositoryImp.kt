package com.example.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.core.media.image.ImageAnalyzedFactory
import com.example.data.model.maping.toData
import com.example.data.model.maping.toDomain
import com.example.data.source.ProductLocalDataSource
import com.example.domain.model.MeasurementMetric
import com.example.domain.model.Product
import com.example.domain.model.ScanningType
import com.example.domain.repository.ProductsRepository
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProductsRepositoryImp @Inject constructor(
    private val dataSource: ProductLocalDataSource,
    private val imageGroceryAnalyzer: ImageAnalyzedFactory
) : ProductsRepository {
    override suspend fun addProduct(product: Product) {
        dataSource.addProduct(product.toData(withId = false))
    }
    override suspend fun removeProductById(id: Int) {
        dataSource.deleteProductById(id)
    }
    override suspend fun updateProduct(product: Product){
        dataSource.updateProduct(product.toData())
    }
    override fun getAllProductsPaging(): Flow<PagingData<Product>> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = { dataSource.getAllProductPaging() }
        ).flow
            .map { pagingData ->
                pagingData.map { it.toDomain() }
            }
    }

    override suspend fun getAllProducts(): List<Product>{
        return dataSource.getAllProduct().map { it.toDomain() }
    }

    override suspend fun getProductById(id: Int): Product {
        return dataSource.getProductById(id).toDomain()
    }

    override suspend fun getAllProductFromPhoto(uri: String,  type: ScanningType): List<Product> {
        return imageGroceryAnalyzer.getImageAnalyzer(type).getProductsFromReceiptImage(uri)
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
