package com.example.myration.ui.AddProductScreen.ScanRecipeScreen

import android.graphics.drawable.BitmapDrawable
import android.icu.util.UniversalTimeScale
import android.net.Uri
import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.drawscope.DrawContext


@Composable
fun ScanImageView(uri :Uri, modifier: Modifier) {

    fun onDraw(canvasHeight: Float, canvasWidth: Float, drawContext: DrawContext) {


    }

    Canvas(modifier = modifier) {
        onDraw(size.height, size.width, drawContext)
    }
}