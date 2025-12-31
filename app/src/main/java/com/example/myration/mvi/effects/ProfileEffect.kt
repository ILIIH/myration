package com.example.myration.mvi.effects

import androidx.compose.runtime.Immutable
import com.example.core.mvi.Reducer
import com.example.myration.mvi.intent.ProfileEvents

@Immutable
sealed class ProfileEffect : Reducer.ViewEffect {
    data class ShowProfileChangeMaxCalorieWidget(val maxCal: Float) : ProfileEffect()
    data object ShowProfileAddEatenProductWidget : ProfileEffect()
}