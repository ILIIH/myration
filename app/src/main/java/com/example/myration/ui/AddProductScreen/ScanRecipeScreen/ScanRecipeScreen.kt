package com.example.myration.ui.AddProductScreen.ScanRecipeScreen

import android.Manifest
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.core.camera.CameraController
import com.example.core.camera.CameraPreviewView
import com.example.myration.R
import com.example.myration.ui.theme.SecondaryBackgroundColor

@Composable
fun ScanRecipeScreen() {
    val context = LocalContext.current
    val controller = remember { CameraController() }

    val cameraPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { granted ->
            if (!granted) {
                Toast.makeText(context , "Camera permission is not granted!", Toast.LENGTH_LONG ).show()
            }
        }
    )

    LaunchedEffect(Unit) {
        cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SecondaryBackgroundColor)
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically)
    ) {
        CameraPreviewView(
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
                .size(50.dp)
                .clickable {
                    controller.takePhoto(
                        context = context,
                        outputDirectory = context.cacheDir,
                        onImageSaved = { uri ->
                            // TODO: Upload the image or display it
                            Toast.makeText(context, "Saved: $uri", Toast.LENGTH_SHORT).show()
                        },
                        onError = { e ->
                            Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                        }
                    )
                }
        )
    }
}
