package com.example.domain.repository

import com.example.domain.model.Filter
import com.example.domain.model.Recipe

interface FiltersRepository {
    fun getAllFilters() : List<Filter>
    fun getFilteredItems(recipes: List<Recipe>, filter: Filter) : List<Recipe>
    fun getNegationSetFilteredItems(recipes: List<Recipe>, filter: Filter) : List<Recipe>
}