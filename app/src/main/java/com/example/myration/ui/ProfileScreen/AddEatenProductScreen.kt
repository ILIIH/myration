package com.example.myration.ui.ProfileScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.ButtonDefaults
import com.example.coreUi.R
import com.example.coreUi.customWindows.SuccessMessage
import com.example.myration.viewModels.ProfileViewModel
import com.example.theme.PrimaryColor

@Composable
fun AddEatenProductScreen(
    viewModel: ProfileViewModel = hiltViewModel(),
    navController: NavHostController
) {
    var productName by remember { mutableStateOf(TextFieldValue()) }
    var productCalorie by remember { mutableStateOf(TextFieldValue()) }
    var productProtein by remember { mutableStateOf(TextFieldValue()) }
    var productFats by remember { mutableStateOf(TextFieldValue()) }
    var productCarbohydrates by remember { mutableStateOf(TextFieldValue()) }

    val isSuccessAddedEatenProduct = viewModel.isSuccessAddedFood.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
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
                .clickable { navController.popBackStack() }
                .padding(2.dp)
        )
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Add new product",
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
                value = productCalorie,
                onValueChange = { productCalorie = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Product calorie (kcal)") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                colors = OutlinedTextFieldDefaults.colors(unfocusedContainerColor = Color.White, focusedContainerColor = Color.White)
            )
            Spacer(modifier = Modifier.height(30.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = productProtein,
                    onValueChange = { productProtein = it },
                    label = { Text("Prot. (g)") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedContainerColor = Color.White,
                        focusedContainerColor = Color.White
                    ),
                    modifier = Modifier.weight(1f)
                )

                OutlinedTextField(
                    value = productFats,
                    onValueChange = { productFats = it },
                    label = { Text("Fats (g)") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedContainerColor = Color.White,
                        focusedContainerColor = Color.White
                    ),
                    modifier = Modifier.weight(1f)
                )

                OutlinedTextField(
                    value = productCarbohydrates,
                    onValueChange = { productCarbohydrates = it },
                    label = { Text("Carbs (g)") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedContainerColor = Color.White,
                        focusedContainerColor = Color.White
                    ),
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(40.dp))
            Button(
                onClick = {
                    viewModel.addEatenProduct(
                        productName = productName.text,
                        calorie = productCalorie.text.toFloat(),
                        p = productProtein.text.toInt(),
                        f = productFats.text.toInt(),
                        c = productCarbohydrates.text.toInt()
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
    if (isSuccessAddedEatenProduct.value) {
        SuccessMessage(
            message = "Successfully added a eaten food",
            onDismiss = {
                navController.popBackStack()
            }
        )
    }
}
