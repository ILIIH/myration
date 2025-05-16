package com.example.myration.ui.AddProductScreen.ScanRecipeScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.theme.SecondaryColor
import com.example.theme.SecondaryHalfTransparentColor
import com.example.theme.Typography

@Composable
fun ImageScannedWidget(scannedText: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 30.dp)
            .height(200.dp)
            .shadow(elevation = 8.dp, shape = RoundedCornerShape(12.dp))
            .border(
                width = 1.dp,
                color = SecondaryHalfTransparentColor,
                shape = RoundedCornerShape(12.dp)
            )
            .background(color = Color.White, shape = RoundedCornerShape(12.dp))
            .padding(vertical = 8.dp, horizontal = 12.dp),
    ) {
        Text(
            text = scannedText,
            style = Typography.titleLarge,
            color = SecondaryColor,
            modifier = Modifier.width(130.dp),
            textAlign = TextAlign.Center
        )
    }
}