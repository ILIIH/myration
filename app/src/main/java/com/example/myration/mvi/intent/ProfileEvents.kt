package com.example.myration.mvi.intent

import androidx.compose.runtime.Immutable
import com.example.core.mvi.Reducer
import com.example.domain.model.CalorieCounter
import com.example.domain.model.Product
import com.example.myration.mvi.state.ProductDetailViewState

@Immutable
sealed class ProfileEvents : Reducer.ViewEvent {
    data object ProfileLoading : ProfileEvents()
    data class GetProfileSetUpStatus(val setUpStatus: Boolean) : ProfileEvents()
    data class ProfileError(val errorMessage: String) : ProfileEvents()
    data class ProfileUpdate(val profileInfo: CalorieCounter) : ProfileEvents()
    data class ProfileLoaded(val profileInfo: CalorieCounter) : ProfileEvents()
}
