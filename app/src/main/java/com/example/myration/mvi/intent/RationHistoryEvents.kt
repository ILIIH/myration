package com.example.myration.mvi.intent

import androidx.compose.runtime.Immutable
import com.example.core.mvi.Reducer
import com.example.domain.model.CalorieCounter
import com.example.domain.model.FoodHistory
import com.example.domain.model.PieChartItem
import com.example.domain.model.Product
import com.example.myration.mvi.state.ProductDetailViewState

@Immutable
sealed class RationHistoryEvents : Reducer.ViewEvent {
    data object RationHistoryLoading : RationHistoryEvents()
    data class RationHistoryError(val errorMessage: String) : RationHistoryEvents()
    data class RationHistoryLoaded(var foodHistoryList: List<List<FoodHistory>>,  var foodMonthSummary:  HashMap<Int,List<PieChartItem>>) : RationHistoryEvents()
}
