package com.example.myration.ui.AddProductScreen.ScanRecipeScreen

import android.content.Context
import android.graphics.Bitmap
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.core_ui.camera.CameraController
import com.example.core_ui.camera.CameraPreviewView
import com.example.myration.R
import com.example.myration.navigation.NavigationRoute
import com.example.myration.viewModels.ScanRecipeViewModel
import com.example.theme.PrimaryColor
import com.example.theme.SecondaryBackgroundColor
import com.example.theme.Typography


@Composable
fun ImageScanningWidget(bitmap: Bitmap, cancelScanning: () -> Unit){
    val scanningProgress = remember { mutableFloatStateOf(0f)   }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SecondaryBackgroundColor)
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically)
    ) {
        Text(
            text = "Scanning progress ${(scanningProgress.floatValue*100).toInt()} %",
            style = Typography.labelSmall,
            color = Color.Black,
            modifier =  Modifier.fillMaxWidth().padding(horizontal = 50.dp, vertical = 10.dp)

        )

        LinearProgressIndicator(
            progress = scanningProgress.floatValue,
            modifier =  Modifier.padding(horizontal = 40.dp, vertical = 10.dp)
        )

        ScanImageView(
            bitmap = bitmap,
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp)
                .padding(horizontal = 5.dp, vertical = 30.dp),
            timerTick = {scanningProgress.floatValue += 1.0f }
        )

        Image(
            painter = painterResource(id = com.example.core_ui.R.drawable.ic_baseline_close),
            contentDescription = "Make a photo",
            modifier = Modifier
                .size(60.dp)
                .shadow(elevation = 1.dp, shape = CircleShape)
                .clip(CircleShape)
                .background(Color.White)
                .clickable {
                    cancelScanning()
                }
                .padding(12.dp)
        )
    }
}