package com.example.myration.viewModels

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.core.media.image.BitmapProvider
import com.example.core.media.image.ImageGroceryAnalyzer
import com.example.myration.state.ImageScanState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class ScanRecipeViewModel @Inject constructor(
    private val imageAnalyzer: ImageGroceryAnalyzer,
    private val bitmapProvider: BitmapProvider
) : ViewModel() {

    private val _scanImageState: MutableStateFlow<ImageScanState> = MutableStateFlow(ImageScanState.PickingImage)
    val scanImageState: StateFlow<ImageScanState> = _scanImageState.asStateFlow()

    fun submitPhoto(photoUri : Uri) {
        Log.i("scanning_image", "Uri = $photoUri" )
        val bitmap = bitmapProvider.getBitmapFromUri(photoUri, 400, 500)
        _scanImageState.value = ImageScanState.ImageScanning(bitmap)
        imageAnalyzer.getTextFromImageUri(photoUri)
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
