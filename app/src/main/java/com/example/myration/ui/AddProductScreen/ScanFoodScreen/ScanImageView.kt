package com.example.myration.ui.AddProductScreen.ScanFoodScreen

import android.graphics.Bitmap
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
import com.example.myration.ui.AddProductScreen.AddProductVoice.TimerManager


@Composable
fun ScanImageView(bitmap: Bitmap?, modifier: Modifier = Modifier, timerTick: () -> Unit) {
    val scanLineY = remember { mutableFloatStateOf(0f)   }
    val scanForward = remember{ mutableStateOf(false) }

    DisposableEffect(Unit) {
        onDispose {
            TimerManager.stop() // stop the timer or clear resources
        }
    }

    fun startTimer(height: Float) {
        TimerManager.start(
            intervalMillis = 100L,
            onTick = {
                timerTick()

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
