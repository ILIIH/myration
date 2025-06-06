package com.example.core.mvi

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow

abstract class BaseViewModel<State : Reducer.ViewState, Event : Reducer.ViewEvent, Effect : Reducer.ViewEffect>(
    initialState: State,
    private val reducer: Reducer<State, Event, Effect>
) : ViewModel() {
    private val _state: MutableStateFlow<State> = MutableStateFlow(initialState)
    val state: StateFlow<State>
        get() = _state.asStateFlow()

    private val _event: MutableSharedFlow<Event> = MutableSharedFlow()
    val event: SharedFlow<Event>
        get() = _event.asSharedFlow()

    private val _effects = Channel<Effect>(capacity = Channel.CONFLATED)
    val effect = _effects.receiveAsFlow()

    val timeCapsule: TimeCapsule<State> = TimeCapsule { storedState ->
        _state.tryEmit(storedState)
    }

    init {
        timeCapsule.saveState(initialState)
    }
    fun sendEffect(effect: Effect) {
        _effects.trySend(effect)
    }

    fun sendEvent(event: Event) {
        val (newState, effect) = reducer.reduce(_state.value, event)

        val success = _state.tryEmit(newState)

        if (success) {
            effect?.let { sendEffect(it) }
            timeCapsule.saveState(newState)
        }
    }
}
