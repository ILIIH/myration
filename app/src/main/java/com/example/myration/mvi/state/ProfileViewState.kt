package com.example.myration.mvi.state

import com.example.core.mvi.Reducer
import com.example.domain.model.CalorieCounter
import com.example.domain.model.PieChartItem

sealed class ProfileViewState(open var info: CalorieCounter = CalorieCounter(0f, 0f, 1, 1, 1), open var foodSummary: List<PieChartItem> = listOf()) : Reducer.ViewState {
    data object ProfileLoading : ProfileViewState()
    data object ProfileInfoSetUp : ProfileViewState()
    data class ProfileLoaded(override var info: CalorieCounter, override var foodSummary: List<PieChartItem>) : ProfileViewState()
    data class ProfileInfoError(val message: String) : ProfileViewState()
}
