package com.example.myration.ui.GroceriesListScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.myration.mvi.effects.GroceriesEffect
import com.example.myration.navigation.NavigationRoute
import com.example.theme.SecondaryBackgroundColor
import com.example.myration.viewModels.GroceriesViewModel

@Composable
fun GroceriesListScreen(
    viewModel: GroceriesViewModel = hiltViewModel(),
    navController: NavHostController
) {
    val productList = viewModel.productList.collectAsLazyPagingItems()
    val screenState = viewModel.state.collectAsStateWithLifecycle()
    val lifecycleOwner = LocalLifecycleOwner.current
    val effectFlow = remember(viewModel.effect, lifecycleOwner) {
        viewModel.effect.flowWithLifecycle(lifecycleOwner.lifecycle, Lifecycle.State.STARTED)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SecondaryBackgroundColor)
    ) {
        SearchWidget()
        GroceriesList(
            productsList = productList,
            removeProduct =  {id -> viewModel.removeProduct(id)},
            navigateToDetailsScreen = { productId ->
                navController.navigate(
                    NavigationRoute.PRODUCT_DETAILS_SCREEN.withArgsProductID(
                        productId
                    )
                )
            }
        )
    }

    LaunchedEffect(Unit) {
        effectFlow.collect { action ->
            when (action) {
                is GroceriesEffect.NavigateToGroceriesDetails -> {

                }
            }
        }
    }

}

