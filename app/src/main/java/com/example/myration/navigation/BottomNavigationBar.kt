package com.example.myration.navigation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.myration.R
import com.example.theme.MyRationTypography
import com.example.theme.PrimaryColor
import com.example.theme.SecondaryBackgroundColor

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val selectedTab = remember { mutableStateOf(NavigationRoute.PROFILE_TAB) }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
            .background(Color.White)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(top = 15.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
                    .clickable {
                        selectedTab.value = NavigationRoute.ADD_PRODUCTS_TAB
                        navController.navigate(NavigationRoute.ADD_PRODUCTS_TAB.route)
                    },
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Image(
                    painter = painterResource(
                        id = if (selectedTab.value == NavigationRoute.ADD_PRODUCTS_TAB) {
                            R.drawable.ic_add_products_selected_tab
                        } else {
                            R.drawable.ic_add_products_not_selected_tab
                        }
                    ),
                    contentDescription = "Add products",
                    modifier = Modifier.size(25.dp)
                )
                Text(
                    text = "Add products",
                    style = MyRationTypography.displayLarge,
                    color = if (selectedTab.value == NavigationRoute.ADD_PRODUCTS_TAB) PrimaryColor else SecondaryBackgroundColor
                )
            }
            Column(
                modifier = Modifier.weight(1f).clickable {
                    selectedTab.value = NavigationRoute.GROCERIES_LIST_TAB
                    navController.navigate(NavigationRoute.GROCERIES_LIST_TAB.route)
                },
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Image(
                    painter = painterResource(
                        id = if (selectedTab.value == NavigationRoute.GROCERIES_LIST_TAB) {
                            R.drawable.ic_my_groceries_selected_tab
                        } else {
                            R.drawable.ic_my_groceries_not_selected_tab
                        }
                    ),
                    contentDescription = "Groceries tab",
                    modifier = Modifier.size(25.dp)
                )
                Text(
                    text = "My groceries",
                    style = MyRationTypography.displayLarge,
                    color = if (selectedTab.value == NavigationRoute.GROCERIES_LIST_TAB) PrimaryColor else SecondaryBackgroundColor
                )
            }
            Column(
                modifier = Modifier.weight(1f)
                    .clickable {
                        selectedTab.value = NavigationRoute.COOKING_TAB
                        navController.navigate(NavigationRoute.COOKING_TAB.route)
                    },
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Image(
                    painter = painterResource(
                        id = if (selectedTab.value == NavigationRoute.COOKING_TAB) {
                            R.drawable.ic_cooking_selected_tab
                        } else {
                            R.drawable.ic_cooking_not_selected_tab
                        }
                    ),
                    contentDescription = "Cooking tab",
                    modifier = Modifier.size(25.dp)
                )
                Text(
                    text = "Cooking",
                    style = MyRationTypography.displayLarge,
                    color = if (selectedTab.value == NavigationRoute.COOKING_TAB) PrimaryColor else SecondaryBackgroundColor
                )
            }
            Column(
                modifier = Modifier.weight(1f)
                    .clickable {
                        selectedTab.value = NavigationRoute.PROFILE_TAB
                        navController.navigate(NavigationRoute.PROFILE_TAB.route)
                    },
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Image(
                    painter = painterResource(
                        id = if (selectedTab.value == NavigationRoute.PROFILE_TAB) {
                            R.drawable.ic_profile_selected_24
                        } else {
                            R.drawable.ic_profile_not_selected_24
                        }
                    ),
                    contentDescription = "Profile tab",
                    modifier = Modifier.size(25.dp)
                )
                Text(
                    text = "Profile",
                    style = MyRationTypography.displayLarge,
                    color = if (selectedTab.value == NavigationRoute.COOKING_TAB) PrimaryColor else SecondaryBackgroundColor
                )
            }
        }
    }
}
