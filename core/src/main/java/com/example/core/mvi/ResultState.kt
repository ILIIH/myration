package com.example.core.mvi

sealed class ResultState<out T> : Reducer.ViewState {
    data object Loading : ResultState<Nothing>()
    data class Success<T>(val data: T) : ResultState<T>()
    data class Error(val message: String, val exception: Throwable? = null) : ResultState<Nothing>()
}
