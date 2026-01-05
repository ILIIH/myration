package com.example.myration.mvi.state

import com.example.core.mvi.Reducer
import com.example.domain.model.CalorieCounter
import com.example.domain.model.FoodHistory

sealed class ProfileViewState(open var info: CalorieCounter? = null, open var foodHistory: List<FoodHistory> = listOf()) : Reducer.ViewState {
    data object ProfileLoading : ProfileViewState()
    data object ProfileInfoSetUp : ProfileViewState()
    data class ProfileLoaded(override var info: CalorieCounter?, override var foodHistory: List<FoodHistory>) : ProfileViewState()
    data class ProfileInfoError(val message: String) : ProfileViewState()
}
