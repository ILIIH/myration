package com.example.myration.ui.DataMap

import androidx.compose.ui.graphics.Color
import com.example.domain.model.CookingDifficulty
import com.example.domain.model.Product
import com.example.domain.model.Recipe
import com.example.theme.NegativeBadgesColor
import com.example.theme.PositiveBadgesColor
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import kotlin.math.abs

fun Recipe.getBadgesDesc():Pair<String, Color>? {
    return when {
        kcal < 300 -> "Low Calories" to PositiveBadgesColor
        kcal > 1300 -> "High Calories" to NegativeBadgesColor
        cookingDifficulty == CookingDifficulty.EASY -> "Easy" to PositiveBadgesColor
        cookingTime.seconds < 1000 -> "Fast" to PositiveBadgesColor
        else -> null
    }
}

fun Product.getBadgesDesc():Pair<String, Color>? {
    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
    val inputDate = LocalDate.parse(expirationDate, formatter)
    val currentDate = LocalDate.now()

    val daysDifference = ChronoUnit.DAYS.between(currentDate, inputDate)
    return when {
        daysDifference < 0 -> "Expired ${abs(daysDifference)} days" to NegativeBadgesColor
        daysDifference in 1..9 -> "Expire in $daysDifference days" to NegativeBadgesColor
        daysDifference in 10..30 -> "Expire in $daysDifference days" to PositiveBadgesColor
        else -> null
    }
}