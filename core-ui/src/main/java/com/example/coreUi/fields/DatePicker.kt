package com.example.coreUi.fields

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.coreUi.R
import com.example.theme.MyRationTypography
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePicker(textPlaceholder: String = "Pick an expiry date", modifier: Modifier = Modifier, onDateSelected: (date: String) -> Unit, dateFormat: String = "dd/MM/yyyy") {
    val datePickerState = rememberDatePickerState()
    val showDialog = remember { mutableStateOf(false) }

    val selectedDateMillis = datePickerState.selectedDateMillis
    val formattedDate = remember(selectedDateMillis) {
        selectedDateMillis?.let {
            SimpleDateFormat(dateFormat, Locale.getDefault()).format(Date(it))
        } ?: ""
    }

    if (showDialog.value) {
        DatePickerDialog(
            onDismissRequest = { showDialog.value = false },
            confirmButton = {
                TextButton(onClick = {
                    showDialog.value = false
                    onDateSelected(formattedDate)
                }) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog.value = false }) {
                    Text("Cancel")
                }
            }
        ) {
            androidx.compose.material3.DatePicker(state = datePickerState)
        }
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(60.dp)
            .shadow(elevation = 8.dp, shape = RoundedCornerShape(4.dp))
            .background(color = Color.White, shape = RoundedCornerShape(4.dp))
            .clickable { showDialog.value = true },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_choose_date),
            contentDescription = "Choose a date",
            modifier = Modifier.size(40.dp).padding(start = 10.dp)
        )
        if (formattedDate.isEmpty()) {
            Text(
                modifier = Modifier.padding(start = 20.dp),
                text = textPlaceholder,
                style = MyRationTypography.displayMedium
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            modifier = Modifier.padding(start = 20.dp),
            text = formattedDate,
            style = MyRationTypography.displayMedium
        )
    }
}
