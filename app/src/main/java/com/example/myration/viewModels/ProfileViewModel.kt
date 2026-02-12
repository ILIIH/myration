package com.example.myration.viewModels

import androidx.lifecycle.viewModelScope
import com.example.core.mvi.BaseViewModel
import com.example.domain.model.GlobalUiState
import com.example.domain.repository.CalorieRepository
import com.example.myration.mvi.effects.ProfileEffect
import com.example.myration.mvi.intent.ProfileEvents
import com.example.myration.mvi.reducer.ProfileReducer
import com.example.myration.mvi.state.ProfileViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val calorieRepository: CalorieRepository
) : BaseViewModel<ProfileViewState, ProfileEvents, ProfileEffect>(
    initialState = ProfileViewState.ProfileLoading,
    reducer = ProfileReducer()
) {
    private val _isSuccessAddedFood = MutableStateFlow(false)
    val isSuccessAddedFood = _isSuccessAddedFood.asStateFlow()

    init {
        viewModelScope.launch {
            if (calorieRepository.checkMaxCalorieSetUp()) {
                val calorieInfo = async { calorieRepository.getCalorieInfo() }
                val foodHistory = async { calorieRepository.getFoodHistory(3) }
                sendEvent(ProfileEvents.ProfileLoaded(profileInfo = calorieInfo.await(), foodHistory = foodHistory.await()))
            } else {
                sendEvent(ProfileEvents.GetProfileSetUpStatus(false))
            }
        }
    }

    fun showChangeMaxCalorie() {
        sendEvent(ProfileEvents.ProfileShowChangeMaxCalorieWidget)
    }
    fun showAddEatenProduct() {
        sendEvent(ProfileEvents.ProfileShowAddEatenProductWidget)
    }
    fun showProfileSetUp() {
        sendEvent(ProfileEvents.ProfileShowSetUpWidget)
    }

    fun calculateMaxCalories(height: String, weight: String, goalWeight: String, age: String, isMale: Boolean): String {
        val h = height.toIntOrNull() ?: 0
        val w = weight.toIntOrNull() ?: 0
        val a = age.toIntOrNull() ?: 0

        var bmr = if (isMale) {
            10 * w + 6.25 * h - 5 * a + 5
        } else {
            10 * w + 6.25 * h - 5 * a - 161
        }
        when {
            goalWeight < weight -> bmr -= 500
            goalWeight > weight -> bmr += 500
        }

        return bmr.toString()
    }
    fun setNewMaxCalories(
        newCalorie: Float,
        updateUICalories: suspend () -> Unit = {}
    ) {
        viewModelScope.launch {
            calorieRepository.setMaxCalorie(newCalorie)
            sendEvent(ProfileEvents.ProfileUpdateCalories(newCalorie))
            withContext(Dispatchers.Main) {
                updateUICalories()
            }
        }
    }
    fun addEatenProduct(
        productName: String,
        calorie: Float,
        p: Int,
        f: Int,
        c: Int,
    ) {
        viewModelScope.launch {
            calorieRepository.addToCurrentCalorie(calorie, productName, p, f, c)
            _isSuccessAddedFood.value = true
        }
    }
}
