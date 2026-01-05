package com.example.myration.viewModels

import androidx.lifecycle.viewModelScope
import com.example.core.mvi.BaseViewModel
import com.example.core.mvi.ResultState
import com.example.domain.repository.FiltersRepository
import com.example.domain.repository.RecipeRepository
import com.example.myration.mvi.effects.CookingEffect
import com.example.myration.mvi.intent.CookingEvents
import com.example.myration.mvi.reducer.CookingReducer
import com.example.myration.mvi.state.CookingViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CookingViewModel @Inject constructor(
    private val recipeRepository: RecipeRepository,
    private val filtersRepository: FiltersRepository
) : BaseViewModel<ResultState<CookingViewState>, CookingEvents, CookingEffect>(
    initialState = ResultState.Loading,
    reducer = CookingReducer()
) {
    init {
        sendEvent(CookingEvents.CookingScreenLoading)
        loadData()
    }
    fun loadData() {
        viewModelScope.launch {
            val recipesList = recipeRepository.getAllRecipe()
            val filtersList = filtersRepository.getAllFilters()
            sendEvent(CookingEvents.CookingScreenLoaded(recipesList, filtersList, recipesList))
        }
    }

    fun navigateToRecipe(id: Int) {
        sendEffect(CookingEffect.NavigateToRecipeDetails(id))
    }
    fun applyFilter(id: Int) {
        sendEvent(CookingEvents.ApplyFilter(id))
    }
    fun removeFilter(id: Int) {
        sendEvent(CookingEvents.RemoveFilter(id))
    }
}
