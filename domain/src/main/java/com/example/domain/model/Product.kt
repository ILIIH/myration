package com.example.domain.model

enum class MeasurementMetric(val desc: String) {
    LITERS("lt"),
    KILOGRAM("kg"),
    PIECE("pcs")
}
data class Product(
    val id: Int,
    val quantity: Float,
    val name: String,
    val measurementMetric: MeasurementMetric,
    val expirationDate: String
)
