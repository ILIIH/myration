package com.example.myration.viewModels

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.media.image.BitmapProvider
import com.example.core.media.image.ImageGroceryAnalyzer
import com.example.domain.repository.ProductsRepository
import com.example.myration.mvi.state.ImageScanState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScanRecipeViewModel @Inject constructor(
    private val productsRepository: ProductsRepository,
    private val bitmapProvider: BitmapProvider
) : ViewModel() {

    private val _scanImageState: MutableStateFlow<ImageScanState> = MutableStateFlow(ImageScanState.PickingImage)
    val scanImageState: StateFlow<ImageScanState> = _scanImageState.asStateFlow()
    fun returnToPickingImage() {
        _scanImageState.value = ImageScanState.PickingImage
    }
    fun submitPhoto(photoUri : Uri) {
        viewModelScope.launch {
            val bitmap = bitmapProvider.getBitmapFromUri(photoUri, 600, 700)
            if(bitmap!= null){
                _scanImageState.value = ImageScanState.ImageScanning(bitmap)
                productsRepository.getAllProductFromRecipe(photoUri.toString())
            }
            else {
                _scanImageState.value = ImageScanState.ImageScanningError(message = "Image reading error")
            }
        }
    }
    fun pickingImageError(error: String ) {
        _scanImageState.value = ImageScanState.ImageScanningError(error)
    }
    fun cancelScanning() {
        _scanImageState.value = ImageScanState.PickingImage
    }
}
