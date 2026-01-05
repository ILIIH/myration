package com.example.myration.mvi.intent

import androidx.compose.runtime.Immutable
import com.example.core.mvi.Reducer
import com.example.domain.model.FoodHistory
import com.example.domain.model.PieChartItem

@Immutable
sealed class RationHistoryEvents : Reducer.ViewEvent {
    data object RationHistoryLoading : RationHistoryEvents()
    data class RationHistoryError(val errorMessage: String) : RationHistoryEvents()
    data class RationHistoryLoaded(var foodHistoryList: List<List<FoodHistory>>, var foodMonthSummary: HashMap<Int, List<PieChartItem>>) : RationHistoryEvents()
}
