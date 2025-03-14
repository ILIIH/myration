package com.example.myration.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.myration.R
import com.example.myration.ui.theme.PrimaryColor
import com.example.myration.ui.theme.SecondaryBackgroundColor
import com.example.myration.view_models.GroceriesViewModel
import com.example.myration.ui.theme.Typography

@Composable
fun GroceriesListScreen(
    viewModel: GroceriesViewModel = hiltViewModel()
) {
    val productList by viewModel.productList.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(SecondaryBackgroundColor)
    ) {
        LazyColumn (modifier = Modifier.padding(top=50.dp)){
            items(
                count = productList.size,
                itemContent = { index ->
                    Row(
                        modifier = Modifier
                            .padding(vertical = 12.dp, horizontal = 24.dp)
                            .fillMaxWidth()
                            .shadow(elevation = 8.dp, shape = RoundedCornerShape(12.dp))
                            .border(width = 1.dp, color = Color.Black, shape = RoundedCornerShape(12.dp))
                            .background(color = Color.White, shape = RoundedCornerShape(12.dp))
                            .padding(vertical = 8.dp, horizontal = 12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterHorizontally)
                    ) {
                        Text(
                            text =productList[index].name,
                            style = Typography.bodyLarge,
                            color = PrimaryColor
                        )
                        Spacer(modifier = Modifier.width(20.dp))
                        Text(
                            text =productList[index].weight.toString(),
                            style = Typography.bodyLarge,
                            color = PrimaryColor
                        )
                        Spacer(modifier = Modifier.width(5.dp))
                        Text(
                            text =productList[index].measurementMetric.desc,
                            style = Typography.bodyLarge,
                            color = PrimaryColor
                        )
                        Spacer(modifier = Modifier.width(20.dp))
                        Image(
                            painter = painterResource(id = R.drawable.ic_baseline_delete_24),
                            contentDescription = "Remove product",
                            modifier = Modifier.size(30.dp).clickable {
                                viewModel.removeProduct(productList[index].id)
                            }
                        )
                    }
                }
            )
        }
    }
}