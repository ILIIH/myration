package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.domain.model.FoodPlanIngredient

@Entity(tableName = "food_plan_ingredients")
data class FoodPlanIngredientEntity(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    val amountGrams: Double,
    val name: String,
    val amountSource: Float,
    val calories: String,
    val foodPlanId: Int,
    val active: Boolean,
    val productId: Int
)

fun FoodPlanIngredientEntity.toData(): FoodPlanIngredient {
    return FoodPlanIngredient(
        amountGrams = this.amountGrams,
        name = this.name,
        amountSource = this.amountSource.toFloat(),
        calories = this.calories.toFloat(),
        foodPlanId = this.foodPlanId,
        active = this.active,
        productId = this.productId
    )
}
