package com.example.myration.reducer

import com.example.core.mvi.Reducer
import com.example.myration.intent.Effects
import com.example.myration.intent.Events
import com.example.myration.state.GroceriesViewState

class GroceriesReducer : Reducer<GroceriesViewState, Events, Effects> {
    override fun reduce(
        previousState: GroceriesViewState,
        event: Events
    ): Pair<GroceriesViewState, Effects?> {
        TODO("Complete MVI soon")
    }
}
