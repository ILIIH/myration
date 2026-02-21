package com.example.myration.ui.AddProductScreen.ScanFoodScreen

import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.coreUi.camera.CameraController
import com.example.myration.R
import com.example.theme.SecondaryBackgroundColor

@Composable
fun PickingImageWidget(context: Context, submitImage: (uri: Uri) -> Unit, errorPickingImage: (error: String) -> Unit) {
    val controller = remember { CameraController() }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(SecondaryBackgroundColor)
    ) {
        com.example.coreUi.camera.CameraPreviewView(
            modifier = Modifier.fillMaxSize(),
            controller = controller
        )

        Image(
            painter = painterResource(id = R.drawable.ic_make_photo_icon),
            contentDescription = "Make a photo",
            modifier = Modifier
                .padding(bottom = 32.dp)
                .size(60.dp)
                .align(Alignment.BottomCenter)
                .shadow(elevation = 8.dp, shape = CircleShape)
                .clip(CircleShape)
                .background(Color.White)
                .clickable {
                    controller.takePhoto(
                        context = context,
                        outputDirectory = context.cacheDir,
                        onImageSaved = { uri -> submitImage(uri) },
                        onError = { e ->
                            Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                        }
                    )
                }
                .padding(12.dp)
        )
    }
}
