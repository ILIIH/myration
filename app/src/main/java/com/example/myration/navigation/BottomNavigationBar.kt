package com.example.myration.navigation

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.myration.R
import com.example.myration.viewModels.MainViewModel
import com.example.theme.MyRationTypography
import com.example.theme.PrimaryColor
import com.example.theme.SecondaryBackgroundColor

@Composable
fun BottomNavigationBar(
    navController: NavHostController,
    mainViewModel: MainViewModel
) {
    val selectedTab = remember { mutableStateOf(NavigationRoute.PROFILE_TAB) }
    val uiState by mainViewModel.uiState.collectAsStateWithLifecycle()
    val subNavigationMenu = remember { mutableStateOf(NavigationRouteSubMenu.GENERAL_MENU_TAB) }

    val animatedSize = animateDpAsState(
        targetValue = if (uiState.isOverlay) 200.dp else 0.dp,
        animationSpec = tween(durationMillis = 1000, easing = LinearOutSlowInEasing),
        label = "SizeAnimation"
    )
    Column {
        navigationSubMenu(
            subNavigationMenu = subNavigationMenu.value,
            animatedSize = animatedSize.value,
            navController = navController,
            mainViewModel = mainViewModel,
            setSubNavigationMenu = { nav -> subNavigationMenu.value = nav }
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp)
                .background(Color.White)
        ) {
            Row(
                modifier = Modifier.padding(top = 15.dp).fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                        .clickable {
                            selectedTab.value = NavigationRoute.RATION_HISTORY_SCREEN
                            navController.navigate(NavigationRoute.RATION_HISTORY_SCREEN.route)
                        },
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Image(
                        painter = painterResource(
                            id = if (selectedTab.value == NavigationRoute.RATION_HISTORY_SCREEN) {
                                com.example.coreUi.R.drawable.ic_history_selected
                            } else {
                                com.example.coreUi.R.drawable.ic_history_unselected
                            }
                        ),
                        contentDescription = "History",
                        modifier = Modifier.size(25.dp)
                    )
                    Text(
                        text = "History",
                        style = MyRationTypography.displaySmall,
                        color = if (selectedTab.value == NavigationRoute.RATION_HISTORY_SCREEN) PrimaryColor else SecondaryBackgroundColor
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
                        style = MyRationTypography.displaySmall,
                        color = if (selectedTab.value == NavigationRoute.GROCERIES_LIST_TAB) PrimaryColor else SecondaryBackgroundColor
                    )
                }
                Surface(
                    onClick = mainViewModel::inverseOverlay,
                    shape = CircleShape,
                    color = Color.White,
                    shadowElevation = 6.dp,
                    modifier = Modifier
                        .size(50.dp)
                        .offset(y = (-30).dp)
                ) {
                    Image(
                        painter = painterResource(com.example.coreUi.R.drawable.ic_add_item),
                        contentDescription = "Add icon",
                        modifier = Modifier.padding(14.dp)
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
                        style = MyRationTypography.displaySmall,
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
                        style = MyRationTypography.displaySmall,
                        color = if (selectedTab.value == NavigationRoute.PROFILE_TAB) PrimaryColor else SecondaryBackgroundColor
                    )
                }
            }
        }
    }
}

@Composable
fun navigationSubMenu(
    subNavigationMenu: NavigationRouteSubMenu,
    animatedSize: Dp,
    navController: NavHostController,
    mainViewModel: MainViewModel,
    setSubNavigationMenu: (menu: NavigationRouteSubMenu) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth()
            .background(Color.Gray.copy(alpha = 0.2f))
            .height(animatedSize),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (subNavigationMenu == NavigationRouteSubMenu.GENERAL_MENU_TAB) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterHorizontally)
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .size(height = 120.dp, width = 100.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .background(Color.White)
                        .clickable {
                            mainViewModel.inverseOverlay()
                            navController.navigate(NavigationRoute.ADD_EATEN_PRODUCT_SCREEN.route)
                        }
                        .padding(14.dp)
                ) {
                    Image(
                        painter = painterResource(R.drawable.ic_cooking_selected_tab),
                        contentDescription = "Add eaten food",
                        modifier = Modifier.size(55.dp)
                    )
                    Text(
                        modifier = Modifier.padding(top = 12.dp),
                        text = "Add meal",
                        style = MyRationTypography.displaySmall,
                        color = PrimaryColor
                    )
                }
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .size(height = 120.dp, width = 100.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .background(Color.White)
                        .clickable {
                            setSubNavigationMenu(NavigationRouteSubMenu.ADD_PRODUCT_TAB)
                        }
                        .padding(14.dp)
                ) {
                    Image(
                        painter = painterResource(com.example.coreUi.R.drawable.ic_add_product_green),
                        contentDescription = "Add product",
                        modifier = Modifier.size(30.dp)
                    )
                    Text(
                        modifier = Modifier.padding(top = 12.dp),
                        text = "Add product",
                        style = MyRationTypography.displaySmall,
                        color = PrimaryColor
                    )
                }
            }
        } else {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start
                ) {
                    Image(
                        painter = painterResource(com.example.coreUi.R.drawable.ic_return),
                        contentDescription = "Return",
                        modifier = Modifier.size(60.dp)
                            .padding(start = 15.dp)
                            .clickable {
                                setSubNavigationMenu(NavigationRouteSubMenu.GENERAL_MENU_TAB)
                            }
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterHorizontally)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .size(height = 100.dp, width = 100.dp)
                            .clip(RoundedCornerShape(20.dp))
                            .background(Color.White)
                            .clickable {
                                mainViewModel.inverseOverlay()
                                navController.navigate(NavigationRoute.SCAN_PRODUCTS_SCREEN.route)
                            }
                            .padding(14.dp)
                    ) {
                        Image(
                            painter = painterResource(R.drawable.ic_add_products_selected_tab),
                            contentDescription = "Scan products",
                            modifier = Modifier.size(40.dp)
                        )
                        Text(
                            modifier = Modifier.padding(top = 12.dp),
                            text = "Scan product",
                            style = MyRationTypography.titleMedium,
                            color = PrimaryColor
                        )
                    }
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .size(height = 100.dp, width = 100.dp)
                            .clip(RoundedCornerShape(20.dp))
                            .background(Color.White)
                            .clickable {
                                mainViewModel.inverseOverlay()
                                navController.navigate(NavigationRoute.ADD_PRODUCT_MANUALLY.route)
                            }
                            .padding(14.dp)
                    ) {
                        Image(
                            painter = painterResource(R.drawable.ic_add_product_manually),
                            contentDescription = "Add manually",
                            modifier = Modifier.size(40.dp)
                        )
                        Text(
                            modifier = Modifier.padding(top = 12.dp),
                            text = "Add manually",
                            style = MyRationTypography.titleMedium,
                            color = PrimaryColor
                        )
                    }
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .size(height = 100.dp, width = 100.dp)
                            .clip(RoundedCornerShape(20.dp))
                            .background(Color.White)
                            .clickable {
                                mainViewModel.inverseOverlay()
                                navController.navigate(NavigationRoute.ADD_PRODUCT_VOICE.route)
                            }
                            .padding(14.dp)
                    ) {
                        Image(
                            painter = painterResource(R.drawable.ic_say_what_did_buy),
                            contentDescription = "Add with voice",
                            modifier = Modifier.size(40.dp)
                        )
                        Text(
                            modifier = Modifier.padding(top = 12.dp),
                            text = "Add by voice",
                            style = MyRationTypography.titleMedium,
                            color = PrimaryColor
                        )
                    }
                }
            }
        }
    }
}
