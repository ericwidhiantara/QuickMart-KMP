package com.luckyfrog.quickmart.features.general.presentation.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.luckyfrog.quickmart.R
import com.luckyfrog.quickmart.core.app.MainViewModel
import com.luckyfrog.quickmart.core.resources.Images
import com.luckyfrog.quickmart.features.category.presentation.categories.CategoryListScreen
import com.luckyfrog.quickmart.features.home.presentation.dashboard.HomeScreen
import com.luckyfrog.quickmart.features.settings.presentation.SettingsScreen


@Composable
fun BottomNavBar(
    mainViewModel: MainViewModel,
    navController: NavController,
    navBarViewModel: NavBarViewModel = hiltViewModel(), // Inject NavBarViewModel

) {
    val currentIndex = navBarViewModel.currentIndex.value // Observe currentIndex


    val navbarItems: Array<BottomNavigationItem> = arrayOf(
        BottomNavigationItem(
            title = R.string.menu_home,
            icon = Images.icMenuHome,
            iconActive = Images.icMenuHomeActive
        ),
        BottomNavigationItem(
            title = R.string.menu_categories,
            icon = Images.icMenuCategories,
            iconActive = Images.icMenuCategoriesActive
        ),
        BottomNavigationItem(
            title = R.string.menu_my_cart,
            icon = Images.icMenuMyCart,
            iconActive = Images.icMenuMyCartActive
        ),
        BottomNavigationItem(
            title = R.string.menu_wishlist,
            icon = Images.icMenuWishlist,
            iconActive = Images.icMenuWishlistActive
        ),
        BottomNavigationItem(
            title = R.string.menu_profile,
            icon = Images.icMenuProfile,
            iconActive = Images.icMenuProfileActive
        ),
    )
    val navbarSize = navbarItems.size - 1
    Scaffold(
        modifier = Modifier,

        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.background
            ) {
                for (i in 0..navbarSize) {
                    val item = navbarItems[i]
                    NavigationBarItem(
                        icon = {
                            Image(
                                painter = painterResource(if (currentIndex == i) item.iconActive else item.icon),
                                contentDescription = stringResource(item.title),
                                colorFilter = ColorFilter.tint(
                                    if (currentIndex == i) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground,
                                    blendMode = BlendMode.SrcIn
                                ),
                            )
                        },
                        label = {
                            Text(
                                stringResource(item.title),
                                color = if (currentIndex == i) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground
                            )
                        },
                        selected = currentIndex == i,
                        onClick = {
                            navBarViewModel.updateIndex(i)
                        }
                    )
                }


            }
        },

        ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            when (currentIndex) {
                0 -> HomeScreen(
                    mainViewModel = mainViewModel,
                    navController = navController,
                )

                1 -> CategoryListScreen(
                    mainViewModel = mainViewModel,
                    navController = navController
                )

                2 -> Text("My Cart")
                3 -> Text("Wishlist")
                4 -> SettingsScreen(navController = navController)
            }
        }
    }
}

data class BottomNavigationItem(
    val title: Int,
    val icon: Int,
    val iconActive: Int
)