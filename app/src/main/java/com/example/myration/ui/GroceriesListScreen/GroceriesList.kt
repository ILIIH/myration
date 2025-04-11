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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import coil.compose.AsyncImage
import com.example.domain.model.Product
import com.example.domain.model.Recipe
import com.example.myration.R
import com.example.myration.ui.CookingScreen.RecipeItemShort
import com.example.theme.SecondaryColor
import com.example.theme.SecondaryHalfTransparentColor
import com.example.theme.Typography

@Composable
fun GroceriesList(productsList: List<Product>, removeProduct: (productId: Int) -> Unit) {
    LazyColumn(modifier = Modifier.padding(top = 50.dp, start = 20.dp, end = 20.dp)) {
        itemsIndexed(productsList.chunked(3)) { index, chunk ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    chunk.take(2).forEach { recipe ->
                        Box(modifier = Modifier.weight(1f)) {
                            ProductItem(productsList[index], removeProduct)
                        }
                    }
                }
            }
    }
}

@Composable
fun ProductItem(product: Product, onDelete: (id: Int) -> Unit) {
    Row(
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
        horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterHorizontally)
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_baseline_close),
            contentDescription = "remove product button",
            modifier = Modifier
                .width(20.dp)
                .height(20.dp)
                .clip(CircleShape)
                .border(2.dp, SecondaryHalfTransparentColor, CircleShape)
                .clickable { onDelete(product.id) }
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "${product.weight.toInt()}  ${product.measurementMetric.desc}",
                style = Typography.displaySmall,
                color = SecondaryColor
            )
            Spacer(modifier = Modifier.height(20.dp))
            Image(
                painter = painterResource(id = R.drawable.ic_my_groceries_selected_tab),
                contentDescription = "product image",
                modifier = Modifier
                    .width(60.dp)
                    .height(60.dp)
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
                text = "exp ${product.expirationDate} ",
                style = Typography.displaySmall,
                color = SecondaryColor
            )
        }
        Spacer(modifier = Modifier.width(20.dp))
    }
}
