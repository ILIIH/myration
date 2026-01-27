package com.example.domain.repository

import com.example.domain.model.Filter

interface FiltersRepository {
    fun getAllFilters(): List<Filter>
}
