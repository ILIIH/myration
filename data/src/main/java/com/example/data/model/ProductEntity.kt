package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.domain.model.MeasurementMetric
import com.example.domain.model.Product

@Entity(tableName = "products")
data class ProductEntity(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    val weight: Float,
    val name: String,
    val measurementMetric: String,
    val expirationDate: String
)

