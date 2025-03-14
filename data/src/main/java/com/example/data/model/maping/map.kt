package com.example.data.model.maping

import com.example.data.model.ProductEntity
import com.example.data.model.RecipeEntity
import com.example.domain.model.CookingDifficulty
import com.example.domain.model.MeasurementMetric
import com.example.domain.model.Product
import com.example.domain.model.Recipe
import com.example.domain.model.RecipeType
import java.sql.Time

// Product

fun ProductEntity.toDomain(): Product {
    val metric = when(this.measurementMetric){
        "lt" -> MeasurementMetric.LITERS
        "kg" -> MeasurementMetric.KILOGRAM
        else -> MeasurementMetric.PIECE
    }
    return Product(this.id,this.weight, this.name, metric, this.expirationDate)
}

fun Product.toData(): ProductEntity {
    return  ProductEntity(
        null,
        this.weight,
        this.name,
        this.measurementMetric.desc,
        this.expirationDate
    )
}

// Recipe

fun RecipeEntity.toDomain():Recipe {
    return  Recipe(
        id = this.id,
        name = this.name,
        cookingTime = Time.valueOf(this.cookingTime),
        cookingDifficulty = CookingDifficulty.fromInt(this.cookingDifficulty),
        kcal = this.kcal,
        type = RecipeType.fromString(this.type),
        recipeCountry = this.recipeCountry,
        description = this.description
    )
}

