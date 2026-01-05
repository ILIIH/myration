package com.example.coreUi.fields

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.theme.MyRationTypography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MeasurementMetricDropdown(setMetric: (metric: String) -> Unit) {
    val expanded = remember { mutableStateOf(false) }
    val items = listOf("kg", "lt", "pcs")
    val selectedItem = remember { mutableStateOf("") }

    ExposedDropdownMenuBox(
        expanded = expanded.value,
        onExpandedChange = { expanded.value = !expanded.value }
    ) {
        TextField(
            value = selectedItem.value,
            onValueChange = {},
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor(),
            label = { Text("Measurement Metric (lt, kg, pcs)", style = MyRationTypography.displayMedium) },
            readOnly = true,
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded.value)
            },
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedContainerColor = Color.White,
                focusedContainerColor = Color.White
            )
        )

        ExposedDropdownMenu(
            expanded = expanded.value,
            onDismissRequest = { expanded.value = false }
        ) {
            items.forEach { metric ->
                DropdownMenuItem(
                    text = { Text(text = metric) },
                    onClick = {
                        selectedItem.value = metric
                        setMetric(metric)
                        expanded.value = false
                    }
                )
            }
        }
    }
}
