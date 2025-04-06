package com.example.core.mvi

class TimeCapsule<T>(private val onRestore: (T) -> Unit) {
    private val stateHistory = mutableListOf<T>()

    fun saveState(state: T) {
        stateHistory.add(state)
    }

    fun restoreState(index: Int) {
        if (index in stateHistory.indices) {
            onRestore(stateHistory[index])
        }
    }
}
