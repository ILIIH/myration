package com.example.myration.viewModels

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.myration.state.ImageScanState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class ScanRecipeViewModel @Inject constructor() : ViewModel() {

    private val _scanImageState: MutableStateFlow<ImageScanState> = MutableStateFlow(ImageScanState.PickingImage)
    val scanImageState: StateFlow<ImageScanState> = _scanImageState.asStateFlow()

    fun submitPhoto(photoUri : Uri) {
        Log.i("scanning_image", "Uri = $photoUri" )
        _scanImageState.value = ImageScanState.ImageScanning(photoUri)
    }
    fun pickingImageError(error: String ) {
        Log.i("scanning_image", "error = $error" )
        _scanImageState.value = ImageScanState.PickingImageError(error)
    }
    fun cancelScanning() {
        Log.i("scanning_image", "cancel " )
        _scanImageState.value = ImageScanState.PickingImage
    }
}
