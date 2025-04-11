package com.example.myration.ui.GroceriesListScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import com.example.domain.model.Product
import com.example.myration.R
import com.example.theme.PrimaryColor
import com.example.theme.SecondaryBackgroundColor
import com.example.theme.Typography
import com.example.myration.viewModels.GroceriesViewModel

@Composable
fun GroceriesListScreen(
    viewModel: GroceriesViewModel = hiltViewModel()
) {
    val productList by viewModel.productList.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SecondaryBackgroundColor)
    ) {
        SearchWidget()
        GroceriesList(productsList = productList, removeProduct =  {id -> viewModel.removeProduct(id)})
    }
}

