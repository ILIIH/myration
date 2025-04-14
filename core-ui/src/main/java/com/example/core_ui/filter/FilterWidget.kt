package com.example.core_ui.filter

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.core_ui.R
import com.example.theme.PrimaryTransparentColor
import com.example.theme.SecondaryBackgroundColor
import com.example.theme.SecondaryColor
import com.example.theme.Typography

@Composable
fun FilterWidget(filters: List<String>) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
            .background(
                color = Color.White,
                shape = RoundedCornerShape(50)
            )
            .border(
                width = 2.dp,
                color = SecondaryColor,
                shape = RoundedCornerShape(50)
            )
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter =  painterResource(id = R.drawable.ic_filters),
                contentDescription = "Filter icon",
                modifier = Modifier.size(24.dp)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = "Add Filters",
                color = Color(0xFFB8876F),
                style = Typography.displaySmall )

            Spacer(modifier = Modifier.width(12.dp))

            Box(
                modifier = Modifier
                    .background(Color.Transparent, RoundedCornerShape(50))
                    .border(1.5.dp, SecondaryColor, RoundedCornerShape(50))
                    .padding(horizontal = 12.dp, vertical = 4.dp)
            ) {
                Text(
                    text = "500kcal <",
                    color = Color(0xFFB8876F),
                    style = Typography.displayLarge,
                )
            }
        }

        Image(
            painter =  painterResource(id = R.drawable.ic_expand_more_24),
            contentDescription = "Expand",
            modifier = Modifier.size(28.dp)
        )
    }
}
