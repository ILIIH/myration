package com.example.myration.mvi.state

import com.example.core.mvi.Reducer
import com.example.domain.model.CalorieCounter

sealed class ProfileViewState(open var info: CalorieCounter? = null ) : Reducer.ViewState {
    data object ProfileInfoSetUp : ProfileViewState()
    data class ProfileLoaded(override var info: CalorieCounter?) : ProfileViewState()
    data class ProfileInfoError(val message: String) : ProfileViewState()

}

