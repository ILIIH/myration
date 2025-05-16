package com.example.myration.ui.GroceriesDetailsScreen

import android.util.Log
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.core.mvi.ResultState
import com.example.core_ui.custom_windows.ConfirmationDialogue
import com.example.core_ui.custom_windows.EditProductDialogue
import com.example.core_ui.list_modifiers.BadgeWidget
import com.example.domain.model.Product
import com.example.domain.model.Recipe
import com.example.myration.R
import com.example.myration.mvi.effects.ProductDetailsEffect
import com.example.myration.mvi.state.ProductDetailViewState
import com.example.myration.navigation.NavigationRoute
import com.example.myration.ui.DataMap.getBadgesDesc
import com.example.myration.viewModels.GroceriesDetailsViewModel
import com.example.theme.PrimaryTransparentColor
import com.example.theme.SecondaryColor
import com.example.theme.SecondaryHalfTransparentColor
import com.example.theme.Typography

@Composable
fun GroceriesDetailsScreen(
    viewModel: GroceriesDetailsViewModel = hiltViewModel(),
    navController: NavHostController
) {
    val productUpload = viewModel.state.collectAsState()
    val showDeleteDialogue = remember{ mutableStateOf(false) }
    val showEditDialogue = remember{ mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            Log.i("reducing_logging", "effect collected -> $effect")

            when (effect) {
                is ProductDetailsEffect.NavigateToRecipeDetails -> {
                    navController.navigate(
                        NavigationRoute.RECIPE_DETAILS_SCREEN.withArgsRecipieID(
                            effect.recipeId
                        )
                    )
                }
                is ProductDetailsEffect.NavigateToGroceriesList -> {
                    navController.navigate(NavigationRoute.GROCERIES_LIST_TAB.route)
                }

            }
        }
    }

    when (val state = productUpload.value) {
        is ResultState.Success -> {
            ProductDetailsLoaded(
                state.data,
                onDeleteProduct = { showDeleteDialogue.value = true},
                onEditProduct = {showEditDialogue.value = true},
                onDishClick = viewModel::navigateToDish
            )
            if(showDeleteDialogue.value){
                ConfirmationDialogue(
                    message = "Are you sure you want to delete product ?",
                    onDismiss = {  showDeleteDialogue.value = false },
                    onConfirm = {
                        viewModel.deleteProduct()
                        showDeleteDialogue.value = false
                    }
                )
            }
            if(showEditDialogue.value){
                EditProductDialogue(
                    product = state.data.product,
                    message = "Edit your product",
                    onDismiss = {showEditDialogue.value = false},
                    onEdit = { productWeight, productName, productMeasurementMetric, productExpiration ->
                        viewModel.editProduct(
                            productWeight,
                            productName,
                            productMeasurementMetric,
                            productExpiration
                        )
                        showEditDialogue.value = false
                    }
                )
            }
        }
        is ResultState.Loading -> {
            CircularProgressIndicator()
        }
        is ResultState.Error -> {
            Text(
                text = "Error: ${state.message}",
                color = Color.Red,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}


@Composable
fun ProductDetailsLoaded(
    state: ProductDetailViewState,
    onDeleteProduct:() -> Unit,
    onEditProduct: () -> Unit,
    onDishClick: (id: Int) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(PrimaryTransparentColor)
    ) {
        ProductTopBar(state.product)
        BadgesList(state.product)
        ActionsButton(onEditProduct, onDeleteProduct)
        DishesList(state.recipes, onDishClick)
    }
}

@Composable
fun DishesList(recipes: List<Recipe>, onDishClick: (recipeId: Int) -> Unit){
    Text(
        modifier = Modifier
            .padding(top = 20.dp)
            .fillMaxWidth(),
        text =  "${recipes.size} recipes found",
        style = Typography.labelLarge,
        color = SecondaryColor,
        textAlign = TextAlign.Center
    )
    LazyColumn(modifier = Modifier
        .padding(top = 10.dp, start = 20.dp, end = 20.dp)
        .height((recipes.size * 300).dp),
        userScrollEnabled = false
    ) {
        items(
            count = recipes.size,
            itemContent = { index ->
                DishesListItem(
                    modifier = Modifier.clickable { onDishClick(recipes[index].id) },
                    recipe = recipes[index]
                )
            }
        )
    }
}

@Composable
fun DishesListItem(modifier: Modifier,recipe: Recipe){
    Column(
        modifier = modifier
            .padding(top = 20.dp)
            .padding(horizontal = 10.dp)
            .fillMaxWidth()
            .height(250.dp)
            .shadow(elevation = 8.dp, shape = RoundedCornerShape(12.dp))
            .border(
                width = 1.dp,
                color = SecondaryHalfTransparentColor,
                shape = RoundedCornerShape(12.dp)
            )
            .background(color = Color.White, shape = RoundedCornerShape(12.dp))
            .padding(vertical = 8.dp, horizontal = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = recipe.type.desc,
            style = Typography.displaySmall,
            color = SecondaryColor
        )
        Spacer(modifier = Modifier.height(20.dp))
        AsyncImage(
            model = recipe.thumbnail,
            contentDescription = "recipe image",
            placeholder = painterResource(R.drawable.ic_baseline_file_download_24),
            error = painterResource(R.drawable.ic_baseline_error_outline_24),
            modifier = Modifier
                .width(70.dp)
                .height(70.dp)
                .clip(CircleShape)
                .border(2.dp, Color.Gray, CircleShape)
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = recipe.name,
            style = Typography.displayLarge,
            color = SecondaryColor,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = recipe.kcal.toString() + " kcal",
            style = Typography.displaySmall,
            color = SecondaryColor
        )
    }
}

@Composable
fun BadgesList(product: Product){
    product.getBadgesDesc()?.let {
        BadgeWidget(it, modifier = Modifier
            .fillMaxWidth()
            .padding(end = 45.dp))
    }
}

@Composable
fun ActionsButton(editProduct: () -> Unit, deleteProduct: () -> Unit) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 45.dp, vertical = 20.dp)) {
        Box(
            modifier = Modifier
                .padding(end = 10.dp)
                .weight(1f)
                .border(
                    width = 1.dp,
                    color = Color.Black,
                    shape = RoundedCornerShape(8.dp)
                )
                .background(Color.White)
                .clickable { editProduct() }
                .padding(horizontal = 8.dp, vertical = 13.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    modifier = Modifier.padding(end = 10.dp),
                    text = "Edit",
                    style = Typography.displaySmall,
                    color = SecondaryColor
                )
                Image(
                    painter = painterResource(id = com.example.core_ui.R.drawable.ic_edit),
                    contentDescription = "edit product icon",
                    modifier = Modifier
                        .size(24.dp)
                )
            }
        }

        Box(
            modifier = Modifier
                .weight(1f)
                .border(
                    width = 1.dp,
                    color = Color.Black,
                    shape = RoundedCornerShape(8.dp)
                )
                .background(Color.White)
                .clickable { deleteProduct() }
                .padding(horizontal = 8.dp, vertical = 13.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    modifier = Modifier.padding(end = 10.dp),
                    text = "Delete",
                    style = Typography.displaySmall,
                    color = SecondaryColor
                )
                Image(
                    painter = painterResource(id = com.example.core_ui.R.drawable.ic_delete),
                    contentDescription = "delete product icon",
                    modifier = Modifier
                        .size(24.dp)
                )
            }
        }
    }
}


@Composable
fun ProductTopBar(product: Product) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp)
            .height(250.dp)
            .padding(vertical = 8.dp, horizontal = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterHorizontally)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.height(15.dp))
            Text(
                text = product.name,
                style = Typography.titleLarge,
                color = SecondaryColor,
                modifier = Modifier.width(130.dp),
                maxLines = 4,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(15.dp))
            Text(
                text = product.name,
                style = Typography.labelSmall,
                color = SecondaryColor,
                modifier = Modifier.width(130.dp),
                maxLines = 4,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center
            )
        }
        Spacer(modifier = Modifier.width(20.dp))
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.height(30.dp))
            Image(
                painter = painterResource(id = R.drawable.ic_my_groceries_selected_tab),
                contentDescription = "product image",
                modifier = Modifier
                    .size(130.dp)
            )
        }
    }
}
