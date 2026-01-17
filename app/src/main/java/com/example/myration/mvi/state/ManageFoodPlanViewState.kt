package com.example.myration.mvi.state

import com.example.core.mvi.Reducer

sealed class ManageFoodPlanViewState : Reducer.ViewState {

    data object Idle : ManageFoodPlanViewState()

    data object Loading : ManageFoodPlanViewState()

    data class Error(val message: String) : ManageFoodPlanViewState()

    companion object {
        fun initial(): ManageFoodPlanViewState = Idle
    }
}
