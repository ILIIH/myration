package com.example.myration.mvi.reducer

import android.util.Log
import com.example.core.mvi.Reducer
import com.example.domain.model.CalorieCounter
import com.example.myration.mvi.effects.ProfileEffect
import com.example.myration.mvi.intent.ProfileEvents
import com.example.myration.mvi.state.ProfileViewState

class ProfileReducer : Reducer<ProfileViewState, ProfileEvents, ProfileEffect> {
    override fun reduce(
        previousState: ProfileViewState,
        event: ProfileEvents
    ): Pair<ProfileViewState, ProfileEffect?> {
        return when(event){
            is ProfileEvents.ProfileLoading -> {
                previousState to ProfileEffect.ShowProfileLoading
            }
            is ProfileEvents.ProfileError -> {
                ProfileViewState.ProfileInfoError(event.errorMessage) to null
            }
            is ProfileEvents.ProfileLoaded -> {
                ProfileViewState.ProfileLoaded(info = event.profileInfo) to null
            }
            is ProfileEvents.ProfileUpdateCalories -> {
                ProfileViewState.ProfileLoaded(info = CalorieCounter(
                    maxCalorie = event.newMaxCalories,
                    currentCalorie = previousState.info?.currentCalorie ?: 0f,
                    protein = previousState.info?.protein ?: 0,
                    fats = previousState.info?.fats ?: 0,
                    carbohydrates = previousState.info?.carbohydrates ?: 0
                )) to null
            }
            is ProfileEvents.ProfileUpdateCalorieCounter -> {
                ProfileViewState.ProfileLoaded(info = CalorieCounter(
                    maxCalorie = previousState.info?.maxCalorie ?: 0f,
                    currentCalorie = previousState.info?.currentCalorie
                        ?: (0f + event.currentCalorie),
                    protein =  previousState.info?.protein
                        ?: (0 + event.protein),
                    fats = previousState.info?.fats
                        ?: (0 + event.fats) ,
                    carbohydrates = previousState.info?.carbohydrates
                        ?: (0 + event.carbohydrates)
                )) to null
            }
            is ProfileEvents.GetProfileSetUpStatus -> {
                if (event.status) {
                    previousState to ProfileEffect.ShowProfileLoading
                } else {
                    ProfileViewState.ProfileInfoSetUp to null
                }
            }
            is ProfileEvents.ProfileShowAddEatenProductWidget -> {
                previousState to ProfileEffect.ShowProfileAddEatenProductWidget
            }
            is ProfileEvents.ProfileShowChangeMaxCalorieWidget -> {
                previousState to ProfileEffect.ShowProfileChangeMaxCalorieWidget(previousState.info?.maxCalorie ?: 0f)
            }
            is ProfileEvents.ProfileShowSetUpWidget -> {
                previousState to null
            }
        }
    }
}
