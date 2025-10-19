package com.example.myration.mvi.state

import android.graphics.Bitmap
import com.example.domain.model.CalorieCounter
import com.example.domain.model.ScanningType
import com.example.domain.model.Product
sealed class ProfileViewState {
    data object ProfileInfoSetUp : ProfileViewState()
    data class ProfileInfoDisplay(val info: CalorieCounter) : ProfileViewState()
    data class ProfileInfoChange(val info: CalorieCounter) : ProfileViewState()
}

