package com.example.myration.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.domain.model.GlobalUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(GlobalUiState())
    val uiState = _uiState.asStateFlow()

    fun setLoading(loading: Boolean) {
        Log.i("ui_general_loading", "isLoading = $loading")
        _uiState.update { it.copy(isLoading = loading) }
    }

    fun showError(message: String) {
        Log.i("ui_general_loading", "showError = $message")
        _uiState.update { it.copy(errorMessage = message, isLoading = false) }
    }

    fun clearError() {
        _uiState.update { it.copy(errorMessage = null) }
    }
}
