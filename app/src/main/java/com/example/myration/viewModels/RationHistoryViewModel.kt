package com.example.myration.viewModels

import androidx.lifecycle.viewModelScope
import com.example.core.mvi.BaseViewModel
import com.example.domain.repository.CalorieRepository
import com.example.myration.mvi.effects.RationHistoryEffect
import com.example.myration.mvi.intent.RationHistoryEvents
import com.example.myration.mvi.reducer.RationHistoryReducer
import com.example.myration.mvi.state.RationHistoryState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RationHistoryViewModel @Inject constructor(private val calorieRepository: CalorieRepository) :
    BaseViewModel<RationHistoryState, RationHistoryEvents, RationHistoryEffect>(
        initialState = RationHistoryState.RationHistoryLoading,
        reducer = RationHistoryReducer()
    ) {
    init {
        viewModelScope.launch {
            val foodHistoryList = calorieRepository.getFullFoodHistory()
            val foodMonthSummary = calorieRepository.getMonthSummary()
            sendEvent(
                RationHistoryEvents.RationHistoryLoaded(
                    foodMonthSummary = foodMonthSummary,
                    foodHistoryList = foodHistoryList
                )
            )
        }
    }
}
