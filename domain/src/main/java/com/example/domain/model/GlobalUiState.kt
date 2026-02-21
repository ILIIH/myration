package com.example.domain.model

data class GlobalUiState(
    val isLoading: Boolean = false,

    val isBottomNavBarHidden: Boolean = false,
    val isOverlay: Boolean = false,
    val errorMessage: String? = null
)
