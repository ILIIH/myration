package com.example.myration.viewModels

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.media.image.BitmapProvider
import com.example.core.media.image.ImageGroceryAnalyzer
import com.example.domain.model.Product
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
                val products = productsRepository.getAllProductFromRecipe(photoUri.toString())
                _scanImageState.value = ImageScanState.ImageScanned(products)
            }
            else {
                _scanImageState.value = ImageScanState.ImageScanningError(message = "Image reading error")
            }
        }
    }
    fun pickingImageError(error: String) {
        _scanImageState.value = ImageScanState.ImageScanningError(error)
    }
    fun cancelScanning() {
        _scanImageState.value = ImageScanState.PickingImage
    }
    fun removeProduct(id: Int) {
        if(scanImageState.value  is ImageScanState.ImageScanned){
            val productList: List<Product> = (scanImageState.value as ImageScanState.ImageScanned).data.filter {
                it.id != id }
            _scanImageState.value = ImageScanState.ImageScanned(productList)
        }
    }
    fun editProduct(product: Product) {
        val productList: List<Product> = (scanImageState.value as ImageScanState.ImageScanned)
            .data
            .filter { it.id != product.id } + product
        _scanImageState.value = ImageScanState.ImageScanned(productList)
    }

    fun submitProducts() {
        if(scanImageState.value  is ImageScanState.ImageScanned) {
            val productList: List<Product> = (scanImageState.value as ImageScanState.ImageScanned).data
            viewModelScope.launch {
                for (product in productList){
                    productsRepository.addProduct(product)
                }
            }
        }
    }
}
