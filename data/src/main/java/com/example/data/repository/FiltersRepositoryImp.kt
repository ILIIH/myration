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