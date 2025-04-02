package com.example.myration.state

import android.net.Uri


sealed class ImageScanState {
    data object PickingImage : ImageScanState()
    data class ImageScanning(val uri: Uri) : ImageScanState()
    data class ImageScanned(val data: String) : ImageScanState()
    data class PickingImageError(val message: String, val exception: Throwable? = null) : ImageScanState()
    data class ScanningError(val message: String, val exception: Throwable? = null) : ImageScanState()

}