package com.example.myration.ui.AddProductScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.myration.R
import com.example.myration.navigation.NavigationRoute
import com.example.theme.PrimaryColor
import com.example.theme.SecondaryBackgroundColor
import com.example.theme.MyRationTypography

@Composable
fun AddProductScreen( navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SecondaryBackgroundColor)
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically)
    ) {
        AddProductOption(
            iconRes = R.drawable.ic_add_products_selected_tab,
            text = "Scan products",
            modifier = Modifier.clickable {
                navController.navigate(NavigationRoute.SCAN_PRODUCTS_SCREEN.route)
            }
        )
        AddProductOption(
            iconRes = R.drawable.ic_add_product_manually,
            text = "Add manually",
            modifier = Modifier.clickable {
                navController.navigate(NavigationRoute.ADD_PRODUCT_MANUALLY.route)
            }
        )
        AddProductOption(
            iconRes = R.drawable.ic_say_what_did_buy,
            text = "Add with voice",
            modifier = Modifier.clickable {
                navController.navigate(NavigationRoute.ADD_PRODUCT_VOICE.route)
            }
        )
    }
}

@Composable
fun AddProductOption(iconRes: Int, text: String, modifier: Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(color = Color.White, shape = RoundedCornerShape(0.dp))
            .padding(vertical = 0.dp, horizontal = 0.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = iconRes),
            contentDescription = text,
            modifier = Modifier.size(70.dp)
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = text,
            style = MyRationTypography.displayMedium,
            color = PrimaryColor
        )
        Spacer(modifier = Modifier.height(10.dp))

    }
}
