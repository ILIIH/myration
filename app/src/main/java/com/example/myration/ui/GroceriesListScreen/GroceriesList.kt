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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.example.core_ui.list_modifiers.BadgeWidget
import com.example.domain.model.Product
import com.example.myration.R
import com.example.myration.ui.DataMap.getBadgesDesc
import com.example.theme.SecondaryColor
import com.example.theme.SecondaryHalfTransparentColor
import com.example.theme.Typography

@Composable
fun GroceriesList(productsList: LazyPagingItems<Product>, removeProduct: (productId: Int) -> Unit, navigateToDetailsScreen : (id: Int) -> Unit) {
    val productItems = productsList.itemSnapshotList.items.chunked(2)
    LazyColumn(
        modifier = Modifier.padding(top = 50.dp, start = 20.dp, end = 20.dp)
    ) {
        items(
            items = productItems,
            key = { chunk -> chunk.firstOrNull()?.id ?: chunk.hashCode() } // unique key per row
        ) { chunk ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                chunk.forEach { product ->
                    ProductItem(
                        product,
                        removeProduct,
                        modifier = Modifier
                            .weight(1f)
                            .clickable {
                                navigateToDetailsScreen(product.id ?: 0)
                            }
                    )
                }
                if (chunk.size == 1) {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }


        productsList.apply {
            when {
                loadState.append is LoadState.Loading -> {
                    item {
                        CircularProgressIndicator(Modifier.padding(16.dp))
                    }
                }
                loadState.append is LoadState.Error -> {
                    val error = loadState.append as LoadState.Error
                    item {
                        Text("Error: ${error.error.localizedMessage}")
                    }
                }
            }
        }
    }

}

@Composable
fun ProductItem(product: Product, onDelete: (id: Int) -> Unit, modifier: Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 15.dp)
            .height(260.dp)
    ) {
        // 1. Product info card (centered)
        Column(
            modifier = Modifier
                .padding(top = 20.dp)
                .shadow(elevation = 8.dp, shape = RoundedCornerShape(12.dp))
                .border(
                    width = 1.dp,
                    color = SecondaryHalfTransparentColor,
                    shape = RoundedCornerShape(12.dp)
                )
                .background(color = Color.White, shape = RoundedCornerShape(12.dp))
                .padding(vertical = 8.dp, horizontal = 12.dp)
                .height(190.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "${product.quantity.toInt()}  ${product.measurementMetric.desc}",
                style = Typography.displaySmall,
                color = SecondaryColor
            )
            Spacer(modifier = Modifier.height(20.dp))

            Image(
                painter = painterResource(id = R.drawable.ic_my_groceries_selected_tab),
                contentDescription = "product image",
                modifier = Modifier
                    .size(60.dp)
            )
            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = product.name,
                style = Typography.titleLarge,
                color = SecondaryColor,
                modifier = Modifier.width(130.dp),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "exp ${product.expirationDate}",
                style = Typography.displaySmall,
                color = SecondaryColor
            )
        }

        // 2. Delete icon (top-left corner, over everything)
        Image(
            painter = painterResource(id = com.example.core_ui.R.drawable.ic_baseline_close),
            contentDescription = "remove product button",
            modifier = Modifier
                .padding(top = 30.dp, start = 10.dp)
                .size(24.dp)
                .align(Alignment.TopStart)
                .clip(CircleShape)
                .border(2.dp, SecondaryHalfTransparentColor, CircleShape)
                .clickable { onDelete(product.id?:0) }
                .padding(2.dp)
        )

        // 3. Product badge (top-right corner)
        product.getBadgesDesc()?.let {
            BadgeWidget(
                badgeDesc = it
            )
        }
    }
}
