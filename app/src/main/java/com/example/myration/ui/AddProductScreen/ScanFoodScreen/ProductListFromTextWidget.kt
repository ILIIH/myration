package com.example.myration.ui.AddProductScreen.ScanFoodScreen

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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import com.example.domain.model.Product
import com.example.myration.R
import com.example.theme.SecondaryColor
import com.example.theme.SecondaryHalfTransparentColor
import com.example.theme.MyRationTypography

@Composable
fun ProductListFromTextWidget(
    products: List<Product>,
    removeProduct: (id: Int)-> Unit,
    editProduct: (product: Product)-> Unit,
    submitProduct:() -> Unit
) {
    Column (
        modifier = Modifier.fillMaxSize()
    ) {
        ScannedProductList(
            productsList = products,
            removeProduct = removeProduct,
            editProduct = editProduct
        )
        SubmitBtn(submitProduct)
    }
}

@Composable
fun ScannedProductList(
    productsList: List<Product>,
    removeProduct: (productId: Int) -> Unit,
    editProduct: (product: Product) -> Unit,
) {
    LazyColumn(modifier = Modifier
        .fillMaxSize()
        .padding(top = 50.dp, start = 20.dp, end = 20.dp, bottom = 30.dp)
    ) {
        itemsIndexed(productsList.chunked(2)) { index, chunk ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(20.dp),
            ) {
                chunk.take(2).forEach { product ->
                    ScannedProductItem(
                        product = product,
                        onDelete = removeProduct,
                        onEdit = editProduct,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}
@Composable
fun SubmitBtn(onSubmit: () -> Unit) {
    Box(
        modifier = Modifier
            .clickable { onSubmit }
            .padding(top = 20.dp)
            .shadow(elevation = 8.dp, shape = RoundedCornerShape(12.dp))
            .border(
                width = 1.dp,
                color = SecondaryHalfTransparentColor,
                shape = RoundedCornerShape(12.dp)
            )
            .background(color = Color.White, shape = RoundedCornerShape(12.dp))
            .padding(vertical = 8.dp, horizontal = 12.dp)
    ){
        Text(
            text = "Submit products",
            style = MyRationTypography.labelSmall,
            color = Color.White
        )
    }
}
@Composable
fun ScannedProductItem(
    product: Product,
    onDelete: (id: Int) -> Unit,
    onEdit: (product: Product) -> Unit,
    modifier: Modifier
) {
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
                style = MyRationTypography.displaySmall,
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
                style = MyRationTypography.titleLarge,
                color = SecondaryColor,
                modifier = Modifier.width(130.dp),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "exp ${product.expirationDate}",
                style = MyRationTypography.displaySmall,
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
                .clickable { onDelete(product.id ?: 0) }
                .padding(2.dp)
        )

        // 3. Edit icon
        Image(
            painter = painterResource(id = com.example.core_ui.R.drawable.ic_edit),
            contentDescription = "edit product button",
            modifier = Modifier
                .padding(top = 30.dp, end = 10.dp)
                .size(24.dp)
                .align(Alignment.TopEnd)
                .clip(CircleShape)
                .border(2.dp, SecondaryHalfTransparentColor, CircleShape)
                .clickable { onEdit(product) }
                .padding(4.dp)
        )
    }
}