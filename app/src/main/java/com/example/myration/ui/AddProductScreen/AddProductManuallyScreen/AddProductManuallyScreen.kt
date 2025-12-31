package com.example.myration.ui.AddProductScreen.AddProductManuallyScreen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.ButtonDefaults
import com.example.core.mvi.ResultState
import com.example.core_ui.custom_windows.ErrorMessage
import com.example.core_ui.custom_windows.SuccessMessage
import com.example.core_ui.fields.DatePicker
import com.example.core_ui.fields.MeasurementMetricDropdown
import com.example.myration.mvi.state.AddProductManuallyViewState
import com.example.theme.PrimaryColor
import com.example.theme.SecondaryBackgroundColor
import com.example.myration.viewModels.AddProductViewModel
import com.example.myration.viewModels.MainViewModel
import com.example.theme.MyRationTypography

@Composable
fun AddProductManuallyScreen(
    viewModel: AddProductViewModel = hiltViewModel(),
    mainViewModel: MainViewModel
) {
    val uploadState = viewModel.state.collectAsState()

    when (val state = uploadState.value) {
        is AddProductManuallyViewState.Idle -> {
            mainViewModel.setLoading(false)
            AddProductManuallyScreenLoaded(viewModel)
        }
        is AddProductManuallyViewState.Loading -> mainViewModel.setLoading(true)
        is AddProductManuallyViewState.Error -> mainViewModel.showError(message = state.message)
        is AddProductManuallyViewState.Loaded -> {
            mainViewModel.setLoading(false)
            SuccessMessage(
                message = "Successfully added a new product",
                onDismiss = {viewModel.returnToAddingStage()}
            )
        }
    }
}

@Composable
fun AddProductManuallyScreenLoaded(viewModel: AddProductViewModel) {
    var productName by remember { mutableStateOf(TextFieldValue("")) }
    var productWeight by remember { mutableStateOf(TextFieldValue("")) }
    var productMeasurementMetric by remember { mutableStateOf("") }
    var productExpiration by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SecondaryBackgroundColor)
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Add product",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                style = MyRationTypography.displayMedium
            )
            Spacer(modifier = Modifier.height(60.dp))
            TextField(
                value = productName,
                onValueChange = { productName = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text(text = "Product name", style = MyRationTypography.displayMedium) },
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(unfocusedContainerColor = Color.White, focusedContainerColor = Color.White)
            )
            Spacer(modifier = Modifier.height(20.dp))
            MeasurementMetricDropdown { productMeasurementMetric = it }
            Spacer(modifier = Modifier.height(20.dp))
            Box(modifier = Modifier.fillMaxWidth()) {
                TextField(
                    value = productWeight,
                    onValueChange = {
                        val cleanInput = it.text.filter { char -> char.isDigit() || char == '.' || char == ',' }
                        productWeight = TextFieldValue(
                            text = cleanInput,
                            selection = TextRange(cleanInput.length)
                        )
                    },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text(text = "Product amount", style = MyRationTypography.displayMedium) },
                    singleLine = true,
                    trailingIcon = {
                        Text(
                            text = productMeasurementMetric, // e.g., "kg"
                            color = Color.Gray,
                            style = MyRationTypography.displayMedium
                        )
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedContainerColor = Color.White,
                        focusedContainerColor = Color.White
                    )
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
            DatePicker { date -> productExpiration = date }
            Spacer(modifier = Modifier.height(20.dp))
            Button(
                onClick = {
                    viewModel.addProduct(
                        productWeight.text,
                        productName.text,
                        productMeasurementMetric,
                        productExpiration
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 10.dp)
                    .shadow(elevation = 8.dp, shape = RoundedCornerShape(12.dp))
                    .background(color = PrimaryColor, shape = RoundedCornerShape(4.dp)),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = PrimaryColor,
                    contentColor = Color.White
                )
            ) {
                Text(text = "Submit", color = Color.White, style = MyRationTypography.displaySmall)
            }
        }
    }
}
