package com.example.core.media.image

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.InputStream

fun decodeSampledBitmapFromStream(
    inputStreamProvider: () -> InputStream,
    reqWidth: Int,
    reqHeight: Int
): Bitmap? {
    // Step 1: Read dimensions only
    val options = BitmapFactory.Options().apply {
        inJustDecodeBounds = true
    }

    // We need to open a fresh InputStream each time — streams can't be reused!
    inputStreamProvider().use { stream ->
        BitmapFactory.decodeStream(stream, null, options)
    }

    // Step 2: Calculate downscaling
    options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight)

    // Step 3: Decode the actual bitmap
    options.inJustDecodeBounds = false

    return inputStreamProvider().use { stream ->
        BitmapFactory.decodeStream(stream, null, options)
    }
}

fun calculateInSampleSize(options: BitmapFactory.Options, reqWidth: Int, reqHeight: Int): Int {
    val (height: Int, width: Int) = options.outHeight to options.outWidth
    var inSampleSize = 1

    if (height > reqHeight || width > reqWidth) {
        val halfHeight: Int = height / 2
        val halfWidth: Int = width / 2

        while (halfHeight / inSampleSize >= reqHeight && halfWidth / inSampleSize >= reqWidth) {
            inSampleSize *= 2
        }
    }

    return inSampleSize
}

