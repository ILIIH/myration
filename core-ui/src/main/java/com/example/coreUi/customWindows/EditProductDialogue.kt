package com.example.coreUi.customWindows

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.ButtonDefaults
import com.example.coreUi.R
import com.example.coreUi.fields.DatePicker
import com.example.coreUi.fields.MeasurementMetricDropdown
import com.example.domain.model.Product
import com.example.theme.PrimaryColor

@Composable
fun EditProductDialogue(
    product: Product,
    message: String,
    onDismiss: () -> Unit,
    onEdit: (
        productWeight: String,
        productName: String,
        productMeasurementMetric: String,
        productExpiration: String
    ) -> Unit
) {
    var productName by remember { mutableStateOf(TextFieldValue(product.name)) }
    var productWeight by remember { mutableStateOf(TextFieldValue(product.quantity.toString())) }
    var productMeasurementMetric by remember { mutableStateOf("") }
    var productExpiration by remember { mutableStateOf(product.expirationDate) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.8f))
            .clickable(onClick = onDismiss),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
            shape = RoundedCornerShape(12.dp),
            elevation = CardDefaults.cardElevation(8.dp)
        ) {
            Column(
                modifier = Modifier
                    .background(Color.White),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_baseline_close),
                    contentDescription = "close window button",
                    modifier = Modifier
                        .padding(top = 20.dp, start = 20.dp, end = 20.dp)
                        .size(32.dp)
                        .align(Alignment.End)
                        .clickable { onDismiss() }
                        .padding(2.dp)
                )
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = message,
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
                            label = { Text("Product amount") },
                            singleLine = true,
                            trailingIcon = {
                                Text(
                                    text = productMeasurementMetric, // e.g., "kg"
                                    color = Color.Gray
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
                            onEdit(
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
                        Text(text = "Submit", color = Color.White)
                    }
                }
            }
        }
    }
}
