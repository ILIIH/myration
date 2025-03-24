package com.example.myration.ui.AddProductScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.ButtonDefaults
import com.example.myration.ui.theme.PrimaryColor
import com.example.myration.ui.theme.SecondaryBackgroundColor
import com.example.myration.view_models.AddProductViewModel

@Composable
fun AddProductManuallyScreen(
    viewModel: AddProductViewModel = hiltViewModel()
) {
        var productName by remember { mutableStateOf(TextFieldValue("")) }
        var productWeight by remember { mutableStateOf(TextFieldValue("")) }
        var productMeasurementMetric by remember { mutableStateOf(TextFieldValue("")) }
        var productExpiration by remember { mutableStateOf(TextFieldValue("")) }

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
                )
                Spacer(modifier = Modifier.height(20.dp))
                TextField(
                    value = productWeight,
                    onValueChange = { productWeight = it },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Product weight") },
                    singleLine = true,
                )
                Spacer(modifier = Modifier.height(20.dp))
                TextField(
                    value = productMeasurementMetric,
                    onValueChange = { productMeasurementMetric = it },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Measurement Metric (lt, kg,pcs)") },
                    singleLine = true,
                )
                Spacer(modifier = Modifier.height(20.dp))
                TextField(
                    value = productExpiration,
                    onValueChange = { productExpiration = it },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Product expiry date") },
                    singleLine = true,
                )
                Spacer(modifier = Modifier.height(20.dp))
                Button(
                    onClick = {
                        viewModel.addProduct(
                            productWeight.text.toFloat()
                            ,productName.text
                            ,productMeasurementMetric.text
                            ,productExpiration.text)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.CenterHorizontally),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = PrimaryColor,
                        contentColor = Color.White
                    )
                ){
                    Text(text = "Submit", color = Color.White)
                }
            }
        }
}