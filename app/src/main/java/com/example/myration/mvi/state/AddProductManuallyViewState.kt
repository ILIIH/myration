package com.example.myration.mvi.state

import com.example.core.mvi.Reducer

sealed class AddProductManuallyViewState : Reducer.ViewState {

    data object Idle : AddProductManuallyViewState()

    data object Loading : AddProductManuallyViewState()

    data object Loaded : AddProductManuallyViewState()

    data class Error(val message: String) : AddProductManuallyViewState()

    companion object {
        fun initial(): AddProductManuallyViewState = Idle
    }
}
