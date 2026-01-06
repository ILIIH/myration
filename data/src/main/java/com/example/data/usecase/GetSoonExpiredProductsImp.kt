package com.example.data.usecase

import com.example.domain.model.Product
import com.example.domain.repository.ProductsRepository
import com.example.domain.usecase.GetSoonExpiredProductsUseCase
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class GetSoonExpiredProductsImp(private val repository: ProductsRepository) : GetSoonExpiredProductsUseCase {
    override suspend fun execute(): List<Product> {
        val calendar = Calendar.getInstance()
        val today: Date = calendar.time

        calendar.add(Calendar.DAY_OF_YEAR, 7)
        val sevenDaysLater: Date = calendar.time

        val datePattern = "dd/MM/yyyy"
        return repository.getAllProducts().filter {
            val sdf = SimpleDateFormat(datePattern, Locale.getDefault())
            val expDate = sdf.parse(it.expirationDate)
            expDate != null && expDate.after(today) && expDate.before(sevenDaysLater)
        }
    }
}
