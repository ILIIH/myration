package com.example.myration.viewModels

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.media.image.BitmapProvider
import com.example.core.media.image.ImageGroceryAnalyzer
import com.example.myration.state.ImageScanState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScanRecipeViewModel @Inject constructor(
    private val imageAnalyzer: ImageGroceryAnalyzer,
    private val bitmapProvider: BitmapProvider
) : ViewModel() {

    private val _scanImageState: MutableStateFlow<ImageScanState> = MutableStateFlow(ImageScanState.PickingImage)
    val scanImageState: StateFlow<ImageScanState> = _scanImageState.asStateFlow()

    fun submitPhoto(photoUri : Uri) {
        val bitmap = bitmapProvider.getBitmapFromUri(photoUri, 600, 700)
        if(bitmap != null) {
            _scanImageState.value = ImageScanState.ImageScanning(bitmap)
            viewModelScope.launch {
                val scannedText = imageAnalyzer.getTextFromImageUri(bitmap)
                _scanImageState.value = ImageScanState.ImageScanned(data = scannedText?:"")
            }
        }
        else{
            _scanImageState.value = ImageScanState.ImageScanningError(message = "Image scanning error")
        }
    }
    fun pickingImageError(error: String ) {
        _scanImageState.value = ImageScanState.PickingImageError(error)
    }
    fun cancelScanning() {
        _scanImageState.value = ImageScanState.PickingImage
    }
}
