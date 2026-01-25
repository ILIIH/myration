package com.example.myration.ui.GroceriesListScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.flowWithLifecycle
import androidx.navigation.NavHostController
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.annotations.DevicePreviews
import com.example.domain.model.MeasurementMetric
import com.example.domain.model.Product
import com.example.myration.mvi.effects.GroceriesEffect
import com.example.myration.navigation.NavigationRoute
import com.example.myration.viewModels.GroceriesViewModel
import com.example.theme.SecondaryBackgroundColor
import kotlinx.coroutines.flow.flowOf

@Composable
fun GroceriesListScreen(
    viewModel: GroceriesViewModel = hiltViewModel(),
    navController: NavHostController
) {
    val productList = viewModel.productList.collectAsLazyPagingItems()
    val lifecycleOwner = LocalLifecycleOwner.current
    val effectFlow = remember(viewModel.effect, lifecycleOwner) {
        viewModel.effect.flowWithLifecycle(lifecycleOwner.lifecycle, Lifecycle.State.STARTED)
    }

    GroceriesLoaded(
        productList = productList,
        removeItem = viewModel::removeProduct,
        navigateToDetailsScreen = { id ->
            navController.navigate(
                NavigationRoute.PRODUCT_DETAILS_SCREEN.withArgsProductID(id)
            )
        }
    )

    LaunchedEffect(Unit) {
        effectFlow.collect { action ->
            when (action) {
                is GroceriesEffect.NavigateToGroceriesDetails -> {
                }
            }
        }
    }
}

@Composable
fun GroceriesLoaded(
    productList: LazyPagingItems<Product>,
    removeItem: (id: Int) -> Unit,
    navigateToDetailsScreen: (id: Int) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SecondaryBackgroundColor)
    ) {
        SearchWidget()
        GroceriesList(
            productsList = productList,
            removeProduct = { id -> removeItem(id) },
            navigateToDetailsScreen = { productId ->
                navigateToDetailsScreen(productId)
            }
        )
    }
}

@DevicePreviews
@Composable
fun PreviewGroceriesListScreen() {
    // 1. Create a list of mock data
    val mockItems = listOf(
        Product(id = 1, name = "Apples", quantity = 20f, measurementMetric = MeasurementMetric.CUPS, expirationDate = "20/07/2000", active = true),
        Product(id = 2, name = "Banana", quantity = 20f, measurementMetric = MeasurementMetric.CUPS, expirationDate = "20/07/2000", active = true)
    )

    // 2. Convert that list into a Flow of PagingData
    val fakeDataFlow = flowOf(PagingData.from(mockItems))

    // 3. Collect it as LazyPagingItems
    val lazyPagingItems = fakeDataFlow.collectAsLazyPagingItems()

    GroceriesLoaded(
        productList = lazyPagingItems,
        removeItem = { },
        navigateToDetailsScreen = { }
    )
}
