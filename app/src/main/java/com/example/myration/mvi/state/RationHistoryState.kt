package com.example.myration.mvi.state

import com.example.core.mvi.Reducer
import com.example.domain.model.CalorieCounter
import com.example.domain.model.FoodHistory
import com.example.domain.model.PieChartItem

sealed class RationHistoryState() : Reducer.ViewState {
    data object RationHistoryLoading : RationHistoryState()
    data class RationHistoryLoaded( var foodHistoryList: List<List<FoodHistory>>,  var foodMonthSummary:  HashMap<Int, List<PieChartItem>>) : RationHistoryState()
    data class RationHistoryError(val message: String) : RationHistoryState()
}

