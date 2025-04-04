package com.example.myration.ui.AddProductScreen.ScanRecipeScreen

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import androidx.compose.foundation.Canvas
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import com.example.core.media.image.decodeSampledBitmapFromStream
import com.example.myration.ui.AddProductScreen.AddProductVoice.TimerManager


@Composable
fun ScanImageView(uri: Uri, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val scanLineY = remember { mutableFloatStateOf(0f)   }
    var scanForward = remember{ mutableStateOf(false) }


    DisposableEffect(Unit) {
        onDispose {
            TimerManager.stop() // stop the timer or clear resources
        }
    }

    fun startTimer(height: Float) {
        TimerManager.start(
            intervalMillis = 100L,
            onTick = {
                if (scanLineY.floatValue >=  height || scanLineY.floatValue<=0) {
                    scanForward.value = !scanForward.value
                }
                if(scanForward.value){
                    scanLineY.floatValue +=20f
                }
                else{
                    scanLineY.floatValue -=20f
                }
            }
        )
    }

    val bitmap = remember(uri) {
        try {
            val originalBitmap = decodeSampledBitmapFromStream(
                inputStreamProvider = { context.contentResolver.openInputStream(uri)!! },
                reqWidth = 800,
                reqHeight = 800
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
                // you can handle more cases if needed
            }

            Bitmap.createBitmap(
                originalBitmap,
                0, 0,
                originalBitmap.width,
                originalBitmap.height,
                matrix,
                true
            )
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    if (bitmap != null) {
        Canvas(modifier = modifier) {
            val canvasWidth = size.width
            val canvasHeight = size.height

            val imageWidth = bitmap.width.toFloat()
            val imageHeight = bitmap.height.toFloat()

            // Calculate the top-left position to center the image
            val offsetX = (canvasWidth - imageWidth) / 2f
            val offsetY = (canvasHeight - imageHeight) / 2f

            drawImage(
                image = bitmap.asImageBitmap(),
                topLeft = Offset(offsetX, offsetY)
            )
            drawLine(
                color = Color.Green,
                strokeWidth = 10f,
                start = Offset(0f, scanLineY.floatValue),
                end = Offset(size.width, scanLineY.floatValue)
            )
            startTimer(canvasHeight)
        }
    } else {
        Text("Failed to load image", modifier = modifier)
    }
}
