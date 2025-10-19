package com.example.myration.mvi.reducer

import android.util.Log
import com.example.core.mvi.Reducer
import com.example.core.mvi.ResultState
import com.example.domain.model.Recipe
import com.example.myration.mvi.effects.AddProductManuallyEffect
import com.example.myration.mvi.effects.ProductDetailsEffect
import com.example.myration.mvi.effects.ProfileEffect
import com.example.myration.mvi.intent.AddProductManuallyEvents
import com.example.myration.mvi.intent.ProductDetailsEvents
import com.example.myration.mvi.intent.ProfileEvents
import com.example.myration.mvi.state.AddProductManuallyViewState
import com.example.myration.mvi.state.ProductDetailViewState
import com.example.myration.mvi.state.ProfileViewState

class ProfileReducer : Reducer<ResultState<ProfileViewState>, ProfileEvents, ProfileEffect> {
    override fun reduce(
        previousState: ResultState<ProfileViewState>,
        event: ProfileEvents
    ): Pair<ResultState<ProfileViewState>, ProfileEffect?> {
        Log.i("reducing_logging", "event reduced -> ${event.javaClass}")
        return when(event){
            is ProfileEvents.ProfileLoading -> {
                ResultState.Loading to null
            }
            is ProfileEvents.ProfileError -> {
                ResultState.Error(event.errorMessage) to null
            }
            is ProfileEvents.ProfileLoaded -> {
                ResultState.Success(ProfileViewState.ProfileInfoDisplay(info = event.profileInfo)) to null
            }
            is ProfileEvents.ProfileUpdate -> {
                ResultState.Success(ProfileViewState.ProfileInfoChange(info = event.profileInfo)) to null
            }
            is ProfileEvents.GetProfileSetUpStatus -> {
                if (event.setUpStatus) {
                    ResultState.Loading to null
                } else {
                    ResultState.Success(ProfileViewState.ProfileInfoSetUp) to ProfileEffect.ShowProfileSetUpDialogue
                }
            }
        }
    }
}
