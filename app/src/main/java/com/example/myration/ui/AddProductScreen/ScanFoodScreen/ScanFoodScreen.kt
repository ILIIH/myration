package com.example.myration.ui.AddProductScreen.ScanFoodScreen

import android.Manifest
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.coreUi.customWindows.EditProductDialogue
import com.example.domain.model.MeasurementMetric
import com.example.domain.model.Product
import com.example.myration.mvi.state.ImageScanState
import com.example.myration.viewModels.MainViewModel
import com.example.myration.viewModels.ScanFoodViewModel

@Composable
fun ScanFoodScreen(
    viewModel: ScanFoodViewModel = hiltViewModel(),
    mainViewModel: MainViewModel
) {
    val context = LocalContext.current
    val screenState = viewModel.scanImageState.collectAsState()
    val productToEdit = remember { mutableStateOf<Product?>(null) }

    val cameraPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { granted ->
            if (!granted) {
                Toast.makeText(context, "Camera permission is not granted!", Toast.LENGTH_LONG).show()
            }
        }
    )

    LaunchedEffect(Unit) {
        cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
    }

    when (val state = screenState.value) {
        is ImageScanState.PickingScanningType -> PickingScanningType(pickScanningType = viewModel::submitScanType)
        is ImageScanState.PickingImage -> PickingImageWidget(
            context = context,
            submitImage = viewModel::submitPhoto,
            errorPickingImage = viewModel::pickingImageError
        )
        is ImageScanState.ImageScanning -> ImageScanningWidget(
            bitmap = state.bitmap,
            cancelScanning = viewModel::cancelScanning
        )
        is ImageScanState.ImageScanned -> {
            ProductListFromTextWidget(
                products = state.data,
                editProduct = { product -> productToEdit.value = product },
                removeProduct = viewModel::removeProduct,
                submitProduct = viewModel::submitProducts
            )
            if (productToEdit.value != null) {
                EditProductDialogue(
                    product = productToEdit.value!!,
                    message = "Edit your product",
                    onDismiss = { productToEdit.value = null },
                    onEdit = { productWeight, productName, productMeasurementMetric, productExpiration ->
                        viewModel.editProduct(
                            Product(
                                id = productToEdit.value!!.id,
                                quantity = productWeight.toFloat(),
                                name = productName,
                                measurementMetric = MeasurementMetric.fromDesc(productMeasurementMetric),
                                expirationDate = productExpiration
                            )
                        )
                        productToEdit.value = null
                    }
                )
            }
        }
        is ImageScanState.ImageScanningError -> mainViewModel.showError(message = state.message)
    }
}
