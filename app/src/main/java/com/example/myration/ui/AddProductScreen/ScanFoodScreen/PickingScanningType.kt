package com.example.myration.ui.AddProductScreen.ScanFoodScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.domain.model.ScanningType
import com.example.myration.R
import com.example.theme.MyRationTypography
import com.example.theme.PrimaryColor

@Composable
fun PickingScanningType(pickScanningType: (type: ScanningType) -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier
                .padding(start = 20.dp, end = 20.dp)
                .shadow(elevation = 8.dp, shape = CircleShape)
                .background(PrimaryColor),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterHorizontally)
        ) {
            Column(
                modifier = Modifier
                    .padding(top = 20.dp)
                    .weight(1f).clickable {
                        pickScanningType(ScanningType.FOOD_SCANNING)
                    },
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_food_icon),
                    contentDescription = "Scan food",
                    modifier = Modifier
                        .size(60.dp)
                        .shadow(elevation = 8.dp, shape = CircleShape)
                        .clip(CircleShape)
                        .background(Color.White)
                        .padding(12.dp)
                )
                Text(
                    modifier = Modifier.padding(top = 20.dp, bottom = 20.dp),
                    text = "Scan food",
                    style = MyRationTypography.displayLarge,
                    color = Color.White
                )
            }
            Column(
                modifier = Modifier
                    .padding(top = 20.dp)
                    .weight(1f).clickable {
                        pickScanningType(ScanningType.RECIPE_SCANNING)
                    },
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_recipt_icon),
                    contentDescription = "Scan receipt",
                    modifier = Modifier
                        .size(60.dp)
                        .shadow(elevation = 8.dp, shape = CircleShape)
                        .clip(CircleShape)
                        .background(Color.White)
                        .padding(12.dp)
                )
                Text(
                    modifier = Modifier.padding(top = 20.dp, bottom = 20.dp),
                    text = "Scan receipt",
                    style = MyRationTypography.displayLarge,
                    color = Color.White
                )
            }
        }
    }
}
