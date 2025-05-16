package com.example.data.model.maping

import com.example.data.model.ProductEntity
import com.example.data.model.RecipeAPIEntity
import com.example.data.model.RecipeEntity
import com.example.data.model.RecipeIngredientEntity
import com.example.domain.model.CookingDifficulty
import com.example.domain.model.MeasurementMetric
import com.example.domain.model.Product
import com.example.domain.model.Recipe
import com.example.domain.model.RecipeIngredient
import com.example.domain.model.RecipeType
import java.time.Duration

// Product

fun ProductEntity.toDomain(): Product {
    val metric = when (this.measurementMetric) {
        "lt" -> MeasurementMetric.LITERS
        "kg" -> MeasurementMetric.KILOGRAM
        else -> MeasurementMetric.PIECE
    }
    return Product(this.id, this.weight, this.name, metric, this.expirationDate)
}

fun Product.toData(): ProductEntity {
    return ProductEntity(
        id = this.id,
        this.quantity,
        this.name,
        this.measurementMetric.desc,
        this.expirationDate
    )
}

// Recipe

fun RecipeEntity.toDomain(): Recipe {
    return Recipe(
        id = this.id ?: 0,
        name = this.name,
        cookingTime = Duration.ofMinutes(this.cookingTime.toLong()),
        cookingDifficulty = CookingDifficulty.fromInt(this.cookingDifficulty),
        kcal = this.kcal,
        type = RecipeType.fromString(this.type),
        recipeCountry = this.recipeCountry,
        description = this.description,
        instructions = this.instructions,
        thumbnail = this.thumbnail,
        videoId = this.youtube
    )
}

fun RecipeAPIEntity.toData(id: Int): RecipeEntity {
    val calPerGramAvg = 2.94
    val calPerGramDesert = 3.86
    var ingKcal = 0

    this.measures.forEach {
        if (it != null) {
            ingKcal = +getGrams(it)
        }
    }

    ingKcal = if (this.category == RecipeType.DESERT.desc) {
        (ingKcal * calPerGramDesert).toInt()
    } else {
        (ingKcal * calPerGramAvg).toInt()
    }
    return RecipeEntity(
        id = id,
        name = this.name ?: "",
        cookingTime = "0", // TO_DO
        cookingDifficulty = (this.ingredients.size / 2.0f).toInt(),
        kcal = ingKcal,
        type = this.category ?: "",
        recipeCountry = this.area ?: "",
        description = this.tags ?: "",
        instructions = this.instructions ?: "",
        thumbnail = this.thumbnail ?: "",
        youtube = this.youtube ?: ""
    )
}

fun RecipeIngredientEntity.toDomain(): RecipeIngredient {
    return RecipeIngredient(
        id = this.id ?: 0,
        recipeID = this.recipeID,
        productName = this.productName,
        productAmount = this.productAmount
    )
}
fun getGrams(text: String): Int {
    val regex = """(\d+)g""".toRegex()
    val combinedNumber = regex.findAll(text)
        .joinToString("") { it.groupValues[1] }

    return combinedNumber.toIntOrNull() ?: 0
}
