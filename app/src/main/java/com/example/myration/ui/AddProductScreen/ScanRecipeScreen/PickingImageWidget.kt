package com.example.myration.ui.AddProductScreen.ScanRecipeScreen

import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.core_ui.camera.CameraController
import com.example.core_ui.camera.CameraPreviewView
import com.example.myration.R
import com.example.theme.SecondaryBackgroundColor


@Composable
fun PickingImageWidget( context: Context, submitImage: (uri: Uri) -> Unit, errorPickingImage: (error: String) -> Unit){
    val controller = remember { CameraController() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SecondaryBackgroundColor)
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically)
    ) {
        com.example.core_ui.camera.CameraPreviewView(
            modifier = Modifier
                .fillMaxWidth()
                .height(500.dp)
                .padding(horizontal = 20.dp, vertical = 30.dp),
            controller = controller
        )

        Image(
            painter = painterResource(id = R.drawable.ic_make_photo_icon),
            contentDescription = "Make a photo",
            modifier = Modifier
                .size(60.dp)
                .shadow(elevation = 8.dp, shape = CircleShape)
                .clip(CircleShape)
                .background(Color.White)
                .clickable {
                    controller.takePhoto(
                        context = context,
                        outputDirectory = context.cacheDir,
                        onImageSaved = { uri ->
                            submitImage(uri)
                        },
                        onError = { e ->
                            Toast
                                .makeText(context, "Error: ${e.message}", Toast.LENGTH_SHORT)
                                .show()
                        }
                    )
                }
                .padding(12.dp)
        )
    }
}