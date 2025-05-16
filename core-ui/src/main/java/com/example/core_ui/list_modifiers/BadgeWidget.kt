package com.example.core_ui.list_modifiers

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.theme.SecondaryHalfTransparentColor
import com.example.theme.Typography

@Composable
fun BadgeWidget(badgeDesc: Pair<String, Color>, modifier: Modifier = Modifier){
    Box(
        modifier = modifier
            .padding(start = 45.dp)
            .background(badgeDesc.second, shape = RoundedCornerShape(8.dp))
            .border(
                width = 1.dp,
                color = SecondaryHalfTransparentColor,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(horizontal = 8.dp, vertical = 13.dp)
            .shadow(elevation = 8.dp, shape = RoundedCornerShape(8.dp))

    ){
        Text(
            text = badgeDesc.first,
            style = Typography.displaySmall,
            color = Color.White
        )
    }
}
