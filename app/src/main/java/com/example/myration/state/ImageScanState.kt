package com.example.myration.state

import android.graphics.Bitmap


sealed class ImageScanState {
    data object PickingImage : ImageScanState()
    data class ImageScanning(val bitmap: Bitmap?) : ImageScanState()
    data class ImageScanned(val data: String) : ImageScanState()
    data class PickingImageError(val message: String, val exception: Throwable? = null) : ImageScanState()
    data class ScanningError(val message: String, val exception: Throwable? = null) : ImageScanState()

}