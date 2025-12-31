package com.example.myration.mvi.reducer

import com.example.core.mvi.Reducer
import com.example.myration.mvi.effects.RationHistoryEffect
import com.example.myration.mvi.intent.RationHistoryEvents
import com.example.myration.mvi.state.RationHistoryState

class RationHistoryReducer : Reducer<RationHistoryState, RationHistoryEvents, RationHistoryEffect> {
    override fun reduce(
        previousState: RationHistoryState,
        event: RationHistoryEvents
    ): Pair<RationHistoryState, RationHistoryEffect?> {
        return when(event){
            is RationHistoryEvents.RationHistoryLoaded -> {
                RationHistoryState.RationHistoryLoaded(foodHistoryList = event.foodHistoryList, foodMonthSummary = event.foodMonthSummary) to null
            }
            is RationHistoryEvents.RationHistoryLoading -> {
                RationHistoryState.RationHistoryLoading to null

            }
            is RationHistoryEvents.RationHistoryError -> {
                RationHistoryState.RationHistoryError(message = event.errorMessage) to null
            }

        }
    }
}
