package com.example.data.repository
import com.example.core.media.image.ImageReceiptAnalyzer
import com.example.data.source.ProductLocalDataSource
import com.example.domain.model.Product
import com.example.domain.model.MeasurementMetric
import io.mockk.*
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class ProductsRepositoryImpTest {

    private lateinit var repository: ProductsRepositoryImp
    private lateinit var localDataSource: ProductLocalDataSource
    private lateinit var imageAnalyzer: ImageReceiptAnalyzer

    @Before
    fun setUp() {
        localDataSource = mockk(relaxed = true)
        imageAnalyzer = mockk()
        repository = ProductsRepositoryImp(localDataSource, imageAnalyzer)
    }

    @Test
    fun `addProduct should call dataSource addProduct`() = runTest {
        val product = Product(name = "Apple", quantity = 2f, measurementMetric = MeasurementMetric.PIECES, expirationDate = "2025-08-01")

        repository.addProduct(product)

        coVerify { localDataSource.addProduct(any()) }
    }

    @Test
    fun `removeProductById should call dataSource deleteProductById`() = runTest {
        val id = 123

        repository.removeProductById(id)

        coVerify { localDataSource.deleteProductById(id) }
    }

    @Test
    fun `updateProduct should call dataSource updateProduct`() = runTest {
        val product = Product(name = "Banana", quantity = 1f, measurementMetric = MeasurementMetric.PIECES, expirationDate = "2025-08-01")

        repository.updateProduct(product)

        coVerify { localDataSource.updateProduct(any()) }
    }

    @Test
    fun `getAllProductFromRecipe should return parsed products`() = runTest {
        val uri = "some/image/path.jpg"
        val expectedProducts = listOf(
            Product(0,2f, "Apple", MeasurementMetric.PIECES, ""),
            Product(0, 1f,"Milk",  MeasurementMetric.LITERS, "")
        )

        coEvery { imageAnalyzer.getProductsFromReceiptImage(uri) } returns expectedProducts

        val result = repository.getAllProductFromPhoto(uri)

        assert(result == expectedProducts)
    }

}
