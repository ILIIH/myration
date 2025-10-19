package com.example.myration.mvi.effects

import androidx.compose.runtime.Immutable
import com.example.core.mvi.Reducer
@Immutable
sealed class ProfileEffect : Reducer.ViewEffect {
    data object ShowProfileSetUpDialogue : ProfileEffect()
}