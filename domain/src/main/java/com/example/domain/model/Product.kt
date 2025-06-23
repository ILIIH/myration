package com.example.domain.model

enum class MeasurementMetric(val desc: String) {
    LITERS("l"),
    MILE_LITERS("ml"),
    GRAM("gramm"),
    KILOGRAM("kg"),
    PIECE("pcs");
    companion object {
        fun fromDesc(desc: String): MeasurementMetric {
            return entries.first { it.desc == desc }
        }
    }
}
data class Product(
    val id: Int? = null,
    val quantity: Float,
    val name: String,
    val measurementMetric: MeasurementMetric,
    val expirationDate: String
)
