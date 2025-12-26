package com.example.core_ui.custom_windows

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.core_ui.R
import com.example.theme.MyRationTypography

@Composable
fun ErrorMessage(
    message: String,
    onDismiss: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.8f))
            .clickable(onClick = onDismiss),
        contentAlignment = Alignment.Center
    ) {
        Card(
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
                Text(
                    text = "Error",
                    style = MyRationTypography.displayLarge,
                    color = Color.Black
                )
                Image(
                    painter = painterResource(id = R.drawable.ic_error_icon),
                    contentDescription = "error icon",
                    modifier = Modifier
                        .padding(top = 40.dp)
                        .padding(horizontal = 70.dp)
                        .size(82.dp)
                        .padding(2.dp)
                )
                Text(
                    modifier = Modifier.padding(top = 40.dp),
                    text = message,
                    style = MyRationTypography.displaySmall,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(35.dp))
            }
        }
    }
}
