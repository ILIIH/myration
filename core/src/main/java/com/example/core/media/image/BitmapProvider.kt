package com.example.core.media.image

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import java.io.InputStream

class BitmapProvider(private val context: Context) {
    fun getBitmapFromUri(uri: Uri, width: Int, height: Int): Bitmap? {
        try {
            val originalBitmap = decodeSampledBitmapFromStream(
                inputStreamProvider = { context.contentResolver.openInputStream(uri)!! },
                reqWidth = width,
                reqHeight = height
            ) ?: throw NullPointerException("originalBitmap is null")

            // Re-open the stream to read EXIF data
            val exifStream = context.contentResolver.openInputStream(uri)
            val exif = ExifInterface(exifStream!!)

            val orientation = exif.getAttributeInt(
                ExifInterface.TAG_ORIENTATION,
                ExifInterface.ORIENTATION_NORMAL
            )

            val matrix = Matrix()

            when (orientation) {
                ExifInterface.ORIENTATION_ROTATE_90 -> matrix.postRotate(90f)
                ExifInterface.ORIENTATION_ROTATE_180 -> matrix.postRotate(180f)
                ExifInterface.ORIENTATION_ROTATE_270 -> matrix.postRotate(270f)
                ExifInterface.ORIENTATION_FLIP_HORIZONTAL -> matrix.preScale(-1f, 1f)
                ExifInterface.ORIENTATION_FLIP_VERTICAL -> matrix.preScale(1f, -1f)
            }

            return Bitmap.createBitmap(
                originalBitmap,
                0,
                0,
                originalBitmap.width,
                originalBitmap.height,
                matrix,
                true
            )
        } catch (e: Exception) {
            return null
        }
    }
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
}
