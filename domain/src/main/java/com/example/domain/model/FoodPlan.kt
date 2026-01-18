package com.example.domain.model

data class FoodPlan(
    val id: Int? = null,
    val mealName: String,
    val mealCalorie: Float,
    val completed: Boolean,
    val completionTime: String,
    val date: String,
    val mealNumber: Int,
    val amountGramsIng: String
)

fun FoodPlan.getMealtimesStr(): String {
    return when (this.mealNumber) {
        1 -> "Breakfast"
        2 -> "Lunch"
        3 -> "Dinner"
        4 -> "Second Breakfast"
        5 -> "Brunch"
        6 -> "Supper"
        else -> "Next Mealtime"
    }
}
