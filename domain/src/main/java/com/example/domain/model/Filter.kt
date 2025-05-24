package com.example.domain.model

data class Filter(
    val id: Int,
    val type: RecipeFilter,
    val isApplied: Boolean,
)

enum class RecipeFilter(val label: String) {
    HIGH_PROTEIN("high protein"),
    EASY("easy"),
    HARD("hard"),
    FAST("fast"),
    LESS_THAN_300_KCAL("300 kcal<"),
    LESS_THAN_500_KCAL("500 kcal<"),
    MORE_THAN_500_KCAL(">500 kcal");

    override fun toString(): String = label
}
