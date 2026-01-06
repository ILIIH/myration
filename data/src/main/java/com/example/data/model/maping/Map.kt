
package com.example.data.model.maping

import com.example.data.model.FoodHistoryEntity
import com.example.data.model.ProductEntity
import com.example.data.model.RecipeAPIEntity
import com.example.data.model.RecipeEntity
import com.example.data.model.RecipeIngredientEntity
import com.example.domain.model.CookingDifficulty
import com.example.domain.model.FoodHistory
import com.example.domain.model.MeasurementMetric
import com.example.domain.model.Product
import com.example.domain.model.Recipe
import com.example.domain.model.RecipeIngredient
import com.example.domain.model.RecipeType
import java.text.SimpleDateFormat
import java.time.Duration
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

const val DATE_FORMAT = "yyyy-MM-dd"
val SDF = SimpleDateFormat(DATE_FORMAT, Locale.getDefault())

fun Date.getString() = this.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().format(DateTimeFormatter.ofPattern(DATE_FORMAT))
// Product

fun ProductEntity.toDomain(): Product {
    val metric = when (this.measurementMetric) {
        "lt" -> MeasurementMetric.LITERS
        "kg" -> MeasurementMetric.KILOGRAM
        else -> MeasurementMetric.PIECES
    }
    return Product(this.id, this.weight, this.name, metric, this.expirationDate)
}

fun Product.toData(withId: Boolean = true): ProductEntity {
    return ProductEntity(
        id = if (withId)this.id else null,
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

// FoodHistory

fun FoodHistoryEntity.toDomain(): FoodHistory {
    return FoodHistory(
        id = this.id ?: 0,
        productName = this.productName,
        productCalorie = this.productCalorie,
        date = SDF.parse(this.date)
    )
}
