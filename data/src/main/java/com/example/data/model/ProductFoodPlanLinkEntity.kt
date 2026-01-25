package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "product_food_plan_links")
data class ProductFoodPlanLinkEntity(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    val productId: Int,
    val foodPlanId: Int,
    val active: Boolean
)
