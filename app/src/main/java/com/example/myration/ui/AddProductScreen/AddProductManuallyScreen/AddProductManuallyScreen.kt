package com.example.myration.ui.AddProductScreen.AddProductManuallyScreen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.ButtonDefaults
import com.example.core.Mvi.ResultState
import com.example.myration.ui.theme.PrimaryColor
import com.example.myration.ui.theme.SecondaryBackgroundColor
import com.example.myration.viewModels.AddProductViewModel

@Composable
fun AddProductManuallyScreen(
    viewModel: AddProductViewModel = hiltViewModel()
) {
    var uploadState = viewModel.addProductState.collectAsState()

    when (val state = uploadState.value) {
        is ResultState.Success -> {
            AddProductManuallyScreenLoaded(viewModel, state.data)
        }
        is ResultState.Loading -> {
            CircularProgressIndicator()
        }
        is ResultState.Error -> {
            Text(
                text = "Error: ${state.message}",
                color = Color.Red,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun AddProductManuallyScreenLoaded(viewModel: AddProductViewModel, loadingState: Boolean) {
    var productName by remember { mutableStateOf(TextFieldValue("")) }
    var productWeight by remember { mutableStateOf(TextFieldValue("")) }
    var productMeasurementMetric by remember { mutableStateOf(TextFieldValue("")) }
    var productExpiration by remember { mutableStateOf(TextFieldValue("")) }
    val context = LocalContext.current

    LaunchedEffect(loadingState) {
        if (loadingState) {
            Toast.makeText(context, "Added product successfully", Toast.LENGTH_SHORT).show()

            productWeight = TextFieldValue("")
            productName = TextFieldValue("")
            productMeasurementMetric = TextFieldValue("")
            productExpiration = TextFieldValue("")
        }
    }

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
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(60.dp))
            TextField(
                value = productName,
                onValueChange = { productName = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Product name") },
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(unfocusedContainerColor = Color.White, focusedContainerColor = Color.White)
            )
            Spacer(modifier = Modifier.height(20.dp))
            TextField(
                value = productWeight,
                onValueChange = { productWeight = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Product weight") },
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(unfocusedContainerColor = Color.White, focusedContainerColor = Color.White)
            )
            Spacer(modifier = Modifier.height(20.dp))
            TextField(
                value = productMeasurementMetric,
                onValueChange = { productMeasurementMetric = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Measurement Metric (lt, kg,pcs)") },
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(unfocusedContainerColor = Color.White, focusedContainerColor = Color.White)
            )
            Spacer(modifier = Modifier.height(20.dp))
            TextField(
                value = productExpiration,
                onValueChange = { productExpiration = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White),
                label = { Text("Product expiry date") },
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(unfocusedContainerColor = Color.White, focusedContainerColor = Color.White)
            )
            Spacer(modifier = Modifier.height(20.dp))
            Button(
                onClick = {
                    viewModel.addProduct(
                        productWeight.text.toFloat(),
                        productName.text,
                        productMeasurementMetric.text,
                        productExpiration.text
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = PrimaryColor,
                    contentColor = Color.White
                )
            ) {
                Text(text = "Submit", color = Color.White)
            }
        }
    }
}
