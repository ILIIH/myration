package com.example.myration.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.mvi.BaseViewModel
import com.example.domain.model.FoodHistory
import com.example.domain.repository.CalorieRepository
import com.example.myration.mvi.effects.ProfileEffect
import com.example.myration.mvi.intent.ProfileEvents
import com.example.myration.mvi.reducer.ProfileReducer
import com.example.myration.mvi.state.ProfileViewState
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
class RationHistoryViewModel @Inject constructor( private val calorieRepository: CalorieRepository ) : ViewModel() {

    private val _foodHistoryList =  MutableStateFlow<List<List<FoodHistory>>>(listOf())
    val foodHistoryList = _foodHistoryList.asStateFlow()

    init {
        viewModelScope.launch {
            _foodHistoryList.value = calorieRepository.getFullFoodHistory()
        }
    }
}
