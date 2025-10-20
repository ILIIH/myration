package com.example.myration.ui.ProfileScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.ButtonDefaults
import com.example.theme.PrimaryColor

@Composable
fun SetUpProfileDialogue(
    onChange: ( newCalorie: Float) -> Unit,
    calculateMaxCalories: (height: String, weight: String, goalWeight: String, age: String, male: Boolean) -> String
) {
    var maxCalorie by remember { mutableStateOf("") }
    var ageTextView by remember { mutableStateOf(TextFieldValue("")) }
    var isMaleCheckBox by remember { mutableStateOf(false) }
    var heightTextView by remember { mutableStateOf(TextFieldValue("")) }
    var currentWeightTextView by remember { mutableStateOf(TextFieldValue("")) }
    var goalWeightTextView by remember { mutableStateOf(TextFieldValue("")) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.8f)),
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
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Lets calculate how much calories you can eat per day to lose weight",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    Spacer(modifier = Modifier.height(60.dp))
                    TextField(
                        value = heightTextView,
                        onValueChange = {
                            heightTextView = it
                            maxCalorie = calculateMaxCalories(
                                heightTextView.text,
                                currentWeightTextView.text,
                                goalWeightTextView.text,
                                ageTextView.text,
                                isMaleCheckBox
                            )},
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text("Height (cm)") },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        colors = OutlinedTextFieldDefaults.colors(unfocusedContainerColor = Color.White, focusedContainerColor = Color.White)
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    TextField(
                        value = ageTextView,
                        onValueChange = {
                            ageTextView = it
                            maxCalorie = calculateMaxCalories(
                                heightTextView.text,
                                currentWeightTextView.text,
                                goalWeightTextView.text,
                                ageTextView.text,
                                isMaleCheckBox
                            )},
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text("Age (years)") },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        colors = OutlinedTextFieldDefaults.colors(unfocusedContainerColor = Color.White, focusedContainerColor = Color.White)
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Checkbox(
                            checked = isMaleCheckBox,
                            onCheckedChange = { isMaleCheckBox = it }
                        )
                        Text(
                            text = "Is male gender",
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                    TextField(
                        value = currentWeightTextView,
                        onValueChange = {
                            currentWeightTextView = it
                            maxCalorie = calculateMaxCalories(
                                heightTextView.text,
                                currentWeightTextView.text,
                                goalWeightTextView.text,
                                ageTextView.text,
                                isMaleCheckBox
                            )},
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text("Current weight (kg)") },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        colors = OutlinedTextFieldDefaults.colors(unfocusedContainerColor = Color.White, focusedContainerColor = Color.White)
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    TextField(
                        value = goalWeightTextView,
                        onValueChange = {
                            goalWeightTextView = it
                            maxCalorie = calculateMaxCalories(
                                heightTextView.text,
                                currentWeightTextView.text,
                                goalWeightTextView.text,
                                ageTextView.text,
                                isMaleCheckBox
                            )},
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text("Goal weight (kg)") },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        colors = OutlinedTextFieldDefaults.colors(unfocusedContainerColor = Color.White, focusedContainerColor = Color.White)
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    TextField(
                        value = maxCalorie,
                        onValueChange = { maxCalorie = it },
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text("Max calorie/day (kcal)") },
                        singleLine = true,
                        enabled = false,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        colors = OutlinedTextFieldDefaults.colors(unfocusedContainerColor = Color.White, focusedContainerColor = Color.White)
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    Button(
                        onClick = {
                            onChange(maxCalorie.toFloat())
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