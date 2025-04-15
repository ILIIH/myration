package com.example.myration.ui.AddProductScreen.AddProductManuallyScreen

import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.google.android.material.datepicker.MaterialDatePicker
import java.util.Date
import java.util.Locale


@Composable
fun DatePicker(label: String, onDateSelected: (date:String) -> Unit) {
    val context = LocalContext.current
    val datePickerDialog = remember { mutableStateOf(false) }
    val selectedDate = remember { mutableStateOf("") }

    if (datePickerDialog.value) {
        val picker = MaterialDatePicker.Builder.datePicker()
            .setTitleText(label)
            .build()

        picker.addOnPositiveButtonClickListener { timeInMillis ->
            val date = java.text.SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                .format(Date(timeInMillis))
            selectedDate.value = date
        }

        LaunchedEffect(Unit) {
            picker.show((context as AppCompatActivity).supportFragmentManager, "DATE_PICKER")
            datePickerDialog.value = false // Reset
        }
    }

    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Selected Date: ${selectedDate.value}")
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = {
            datePickerDialog.value = true
            onDateSelected(selectedDate.value)
        }) {
            Text("Pick a date")
        }
    }
}
