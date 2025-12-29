package com.example.myration.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.mvi.BaseViewModel
import com.example.domain.model.FoodHistory
import com.example.domain.model.PieChartItem
import com.example.domain.repository.CalorieRepository
import com.example.myration.mvi.effects.ProfileEffect
import com.example.myration.mvi.effects.RationHistoryEffect
import com.example.myration.mvi.intent.ProfileEvents
import com.example.myration.mvi.intent.RationHistoryEvents
import com.example.myration.mvi.reducer.ProfileReducer
import com.example.myration.mvi.reducer.RationHistoryReducer
import com.example.myration.mvi.state.ProfileViewState
import com.example.myration.mvi.state.RationHistoryState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.Instant
import java.time.ZoneId
import javax.inject.Inject

@HiltViewModel
class RationHistoryViewModel @Inject constructor( private val calorieRepository: CalorieRepository )
    : BaseViewModel<RationHistoryState, RationHistoryEvents, RationHistoryEffect>(
    initialState = RationHistoryState.RationHistoryLoading,
    reducer = RationHistoryReducer()
)
{
    init {
        viewModelScope.launch {
            val foodHistoryList = calorieRepository.getFullFoodHistory()
            val foodMonthSummary = calorieRepository.getMonthSummary()
            sendEvent(RationHistoryEvents.RationHistoryLoaded(
                foodMonthSummary = foodMonthSummary,
                foodHistoryList = foodHistoryList
            ))
        }
    }
}
