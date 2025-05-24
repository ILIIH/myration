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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.core_ui.camera.CameraController
import com.example.core_ui.camera.CameraPreviewView
import com.example.core_ui.custom_windows.EditProductDialogue
import com.example.core_ui.custom_windows.ErrorMessage
import com.example.domain.model.MeasurementMetric
import com.example.domain.model.Product
import com.example.myration.R
import com.example.myration.mvi.state.ImageScanState
import com.example.theme.SecondaryBackgroundColor
import com.example.myration.viewModels.ScanRecipeViewModel

@Composable
fun ScanRecipeScreen(
    viewModel: ScanRecipeViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val screenState = viewModel.scanImageState.collectAsState()
    val productToEdit = remember{ mutableStateOf<Product?>(null) }

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

    when(val state = screenState.value)  {
        ImageScanState.PickingImage -> PickingImageWidget(
            context = context,
            submitImage = viewModel::submitPhoto,
            errorPickingImage = viewModel::pickingImageError
        )
        is ImageScanState.ImageScanning ->ImageScanningWidget(
            bitmap  = state.bitmap,
            cancelScanning = viewModel::cancelScanning,
        )
        is ImageScanState.ImageScanned -> {
            ImageScannedWidget(
                products = state.data,
                editProduct = {product -> productToEdit.value = product},
                removeProduct = viewModel::removeProduct,
                submitProduct = viewModel::submitProducts
            )
            if(productToEdit.value!= null){
                EditProductDialogue(
                    product = productToEdit.value!!,
                    message = "Edit your product",
                    onDismiss = {productToEdit.value = null},
                    onEdit = { productWeight, productName, productMeasurementMetric, productExpiration ->
                        viewModel.editProduct(
                            Product(
                                id = productToEdit.value!!.id,
                                quantity = productWeight.toFloat(),
                                name = productName,
                                measurementMetric = MeasurementMetric.fromDesc(productMeasurementMetric),
                                expirationDate =  productExpiration
                            )
                        )
                        productToEdit.value = null
                    }
                )
            }
        }
        is ImageScanState.ImageScanningError -> ErrorMessage(
            message = state.message,
            onDismiss = {viewModel.returnToPickingImage()}
        )
    }
}
