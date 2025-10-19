package com.example.data.model

data class MealsResponse(
    val meals: List<RecipeAPIEntity>?
)
data class RecipeAPIEntity(
    val id: Int,
    val name: String?,
    val alternateName: String? = null,
    val category: String?,
    val area: String?,
    val instructions: String?,
    val thumbnail: String?,
    val tags: String? = null,
    val youtube: String? = null,
    val source: String? = null,
    val imageSource: String? = null,
    val creativeCommonsConfirmed: String? = null,
    val dateModified: String? = null,
    val ingredients: List<String?>,
    val measures: List<String?>
)
