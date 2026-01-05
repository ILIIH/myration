package com.example.myration.mvi.state

import android.graphics.Bitmap
import com.example.domain.model.Product
import com.example.domain.model.ScanningType
sealed class ImageScanState {
    data class PickingImage(val type: ScanningType) : ImageScanState()
    data object PickingScanningType : ImageScanState()
    data class ImageScanning(val bitmap: Bitmap) : ImageScanState()
    data class ImageScanned(val data: List<Product>) : ImageScanState()
    data class ImageScanningError(val message: String, val exception: Throwable? = null) : ImageScanState()
}
