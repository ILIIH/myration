package com.example.myration.mvi.effects

import androidx.compose.runtime.Immutable
import com.example.core.mvi.Reducer
@Immutable
sealed class AddProductManuallyEffect : Reducer.ViewEffect {
    data class NavigateToGroceriesDetails(val productId: String) : AddProductManuallyEffect()
}
