package com.example.myration.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.wear.compose.material.Button
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.wear.compose.material.ButtonDefaults
import com.example.myration.R
import com.example.myration.ui.theme.PrimaryColor
import com.example.myration.ui.theme.SecondaryBackgroundColor
import com.example.myration.ui.theme.Typography
import com.example.myration.view_models.AddProductViewModel

@Composable
fun AddProductScreen(
    viewModel: AddProductViewModel = hiltViewModel()
) {
    var isManuallyAddVisible by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SecondaryBackgroundColor)
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically)
    ) {
        AddProductOption(
            iconRes = R.drawable.ic_add_products_selected_tab,
            text = "Scan recipe",
            modifier = Modifier.clickable { }
        )
        AddProductOption(
            iconRes = R.drawable.ic_add_product_manually,
            text = "Add manually",
            modifier = Modifier.clickable { isManuallyAddVisible = true }
        )
        AddProductOption(
            iconRes = R.drawable.ic_say_what_did_buy,
            text = "Add with voice",
            modifier = Modifier.clickable { }
        )

        if (isManuallyAddVisible) {
            ManuallyAddProduct(
                onDismissRequest = { isManuallyAddVisible = false },
                onAddProduct = {weight ,name , measurementMetric, expirationDate ->
                    viewModel.addProduct(weight,name,measurementMetric,expirationDate)
                    isManuallyAddVisible = false}
            )
        }
    }
}

@Composable
fun AddProductOption(iconRes: Int, text: String, modifier: Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .shadow(elevation = 8.dp, shape = RoundedCornerShape(12.dp))
            .border(width = 1.dp, color = Color.Black, shape = RoundedCornerShape(12.dp))
            .background(color = Color.White, shape = RoundedCornerShape(12.dp))
            .padding(vertical = 12.dp, horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = iconRes),
            contentDescription = text,
            modifier = Modifier.size(70.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = text,
            style = Typography.titleLarge,
            color = PrimaryColor
        )
    }
}

@Composable
fun ManuallyAddProduct(onDismissRequest: () -> Unit,
                       onAddProduct: (weight: Float,
                                      name: String,
                                      measurementMetric: String,
                                      expirationDate: String)->Unit) {
    Dialog(onDismissRequest = onDismissRequest) {
        var productName by remember { mutableStateOf(TextFieldValue("")) }
        var productWeight by remember { mutableStateOf(TextFieldValue("")) }
        var productMeasurementMetric by remember { mutableStateOf(TextFieldValue("")) }
        var productExpiration by remember { mutableStateOf(TextFieldValue("")) }

        Surface(
            shape = MaterialTheme.shapes.extraLarge,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .background(Color.White)
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
                        onAddProduct(
                            productWeight.text.toFloat(),
                            productName.text,
                            productMeasurementMetric.text,
                            productExpiration.text,  )
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
}