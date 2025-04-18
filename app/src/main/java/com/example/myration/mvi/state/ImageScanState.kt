package com.example.myration.mvi.state

import android.graphics.Bitmap
import android.net.Uri


sealed class ImageScanState {
    data object PickingImage : ImageScanState()
    data class ImageScanning(val bitmap: Bitmap) : ImageScanState()
    data class ImageScanned(val data: String) : ImageScanState()
    data class PickingImageError(val message: String, val exception: Throwable? = null) : ImageScanState()
    data class ImageScanningError(val message: String, val exception: Throwable? = null) : ImageScanState()

}