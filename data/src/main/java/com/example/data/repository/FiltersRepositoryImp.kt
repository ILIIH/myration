package com.example.data.repository

import com.example.domain.model.CookingDifficulty
import com.example.domain.model.Filter
import com.example.domain.model.Recipe
import com.example.domain.model.RecipeFilter
import com.example.domain.model.RecipeType
import com.example.domain.repository.FiltersRepository

class FiltersRepositoryImp : FiltersRepository {

    // TODO("Change implementation")
    override fun getAllFilters(): List<Filter> {
        return filterList.mapIndexed { index, filter ->
            Filter(id = index, type = filter, isApplied = false)
        }
    }

    // TODO("Change implementation to flow API")
    override fun getFilteredItems(recipes: List<Recipe>, filter: Filter): List<Recipe> {
        return when (filter.type) {
            RecipeFilter.HIGH_PROTEIN -> {
                recipes.filter { it.type == RecipeType.HIGH_PROTEIN }
            }
            RecipeFilter.EASY -> {
                recipes.filter { it.cookingDifficulty == CookingDifficulty.EASY }
            }
            RecipeFilter.HARD -> {
                recipes.filter { it.cookingDifficulty == CookingDifficulty.HARD } // FIXED
            }
            RecipeFilter.FAST -> {
                recipes.filter { it.cookingTime.seconds < 1000 }
            }
            RecipeFilter.LESS_THAN_300_KCAL -> {
                recipes.filter { it.kcal < 300 }
            }
            RecipeFilter.LESS_THAN_500_KCAL -> {
                recipes.filter { it.kcal < 500 }
            }
            RecipeFilter.MORE_THAN_500_KCAL -> {
                recipes.filter { it.kcal > 500 }
            }
        }
    }

    override fun getNegationSetFilteredItems(recipes: List<Recipe>, filter: Filter) : List<Recipe> {
        return when (filter.type) {
            RecipeFilter.HIGH_PROTEIN -> {
                recipes.filter { it.type == RecipeType.HIGH_PROTEIN }
            }
            RecipeFilter.EASY -> {
                recipes.filter { it.cookingDifficulty == CookingDifficulty.EASY }
            }
            RecipeFilter.HARD -> {
                recipes.filter { it.cookingDifficulty == CookingDifficulty.HARD } // FIXED
            }
            RecipeFilter.FAST -> {
                recipes.filter { it.cookingTime.seconds < 1000 }
            }
            RecipeFilter.LESS_THAN_300_KCAL -> {
                recipes.filter { it.kcal < 300 }
            }
            RecipeFilter.LESS_THAN_500_KCAL -> {
                recipes.filter { it.kcal < 500 }
            }
            RecipeFilter.MORE_THAN_500_KCAL -> {
                recipes.filter { it.kcal > 500 }
            }
        }
    }

    companion object {
        private val filterList = listOf(
            RecipeFilter.HIGH_PROTEIN,
            RecipeFilter.EASY,
            RecipeFilter.HARD,
            RecipeFilter.FAST,
            RecipeFilter.LESS_THAN_300_KCAL,
            RecipeFilter.LESS_THAN_500_KCAL,
            RecipeFilter.MORE_THAN_500_KCAL
        )
    }
}