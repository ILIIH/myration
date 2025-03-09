package com.example.domain.model

enum class MeasurementMetric(val desc: String) {
    LITERS("litters"),
    KILOGRAM("kilogram"),
    PIECE("pcs"),
}
data class Product(
    val id: Int? = null,
    val weight: Float ,
    val name: String,
    val measurementMetric: MeasurementMetric
)