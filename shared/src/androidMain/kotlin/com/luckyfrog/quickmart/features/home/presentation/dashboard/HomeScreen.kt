package com.luckyfrog.quickmart.features.home.presentation.dashboard

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.luckyfrog.quickmart.R
import com.luckyfrog.quickmart.core.app.MainViewModel
import com.luckyfrog.quickmart.core.resources.Images
import com.luckyfrog.quickmart.features.general.presentation.NavBarViewModel
import com.luckyfrog.quickmart.features.home.presentation.dashboard.component.Carousel
import com.luckyfrog.quickmart.features.home.presentation.dashboard.component.CategoryList
import com.luckyfrog.quickmart.features.home.presentation.dashboard.component.ProfileImage
import com.luckyfrog.quickmart.features.product.domain.entities.ProductFormParamsEntity
import com.luckyfrog.quickmart.features.product.presentation.product_list.ProductListViewModel
import com.luckyfrog.quickmart.features.product.presentation.product_list.ProductState
import com.luckyfrog.quickmart.features.product.presentation.product_list.component.ProductCard
import com.luckyfrog.quickmart.features.profile.presentation.profile.UserState
import com.luckyfrog.quickmart.features.profile.presentation.profile.UserViewModel
import com.luckyfrog.quickmart.utils.resource.route.AppScreen
import com.luckyfrog.quickmart.utils.resource.theme.AppTheme
import org.koin.androidx.compose.koinViewModel
import java.util.Locale

@Composable
fun HomeScreen(
    navController: NavController,
    mainViewModel: MainViewModel = koinViewModel<MainViewModel>(),
    userViewModel: UserViewModel = koinViewModel<UserViewModel>(),
    navBarViewModel: NavBarViewModel = koinViewModel<NavBarViewModel>(),
) {
    val userState by userViewModel.userState.collectAsState()

    LaunchedEffect(Unit) {
        userViewModel.getUserLogin()
    }

    Scaffold(
        modifier = Modifier
            .padding(vertical = 10.dp)
            .background(MaterialTheme.colorScheme.background),
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Image(
                    painter = painterResource(
                        when (mainViewModel.stateApp.theme) {
                            AppTheme.Light -> Images.icLogoDark
                            AppTheme.Dark -> Images.icLogoLight
                            AppTheme.Default -> if (isSystemInDarkTheme()) Images.icLogoLight else Images.icLogoDark
                        }
                    ),
                    modifier = Modifier.height(32.dp),
                    contentDescription = "Logo"
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    content = {
                        IconButton(onClick = {
                            navController.navigate(AppScreen.SearchScreen.route)
                        }) {
                            Image(
                                painter = painterResource(Images.icSearch),
                                contentDescription = "Search",
                                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onBackground)
                            )
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Button(
                            modifier = Modifier.width(42.dp),
                            contentPadding = PaddingValues(0.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                            shape = RoundedCornerShape(8.dp),
                            onClick = { /*TODO*/ }
                        ) {
                            when (val state = userState) {
                                is UserState.Success -> {
                                    ProfileImage(
                                        state.data.profilePicture
                                            ?: "https://via.placeholder.com/48x48"
                                    )
                                }

                                is UserState.Error, is UserState.Idle -> {
                                    ProfileImage("https://via.placeholder.com/48x48")
                                }

                                is UserState.Loading -> {
                                    CircularProgressIndicator(
                                        modifier = Modifier
                                            .size(36.dp)
                                            .clip(RoundedCornerShape(8.dp))
                                    )
                                }

                            }
                        }
                    }
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
        ) {
            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
            ) {
                Carousel()
                Spacer(modifier = Modifier.height(24.dp))
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = stringResource(R.string.categories),
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                    TextButton(
                        onClick = {
                            navBarViewModel.updateIndex(1)
                        }
                    ) {
                        Text(
                            text = stringResource(R.string.see_all).uppercase(Locale.getDefault()),
                            fontSize = 12.sp
                        )
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))
                CategoryList(navController = navController)
                Spacer(modifier = Modifier.height(24.dp))
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = stringResource(R.string.latest_products),
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                    val context = LocalContext.current
                    val title = context.getString(R.string.latest_products)
                    TextButton(
                        onClick = {
                            navController.navigate(
                                "${AppScreen.ProductListScreen.route}?title=$title"
                            )
                        }
                    ) {
                        Text(
                            text = stringResource(R.string.see_all).uppercase(Locale.getDefault()),
                            fontSize = 12.sp
                        )
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))
            }

            // Product Grid Section
            HomeProductGrid(
                navController = navController
            )
            Spacer(modifier = Modifier.height(24.dp))

        }
    }
}

@Composable
private fun HomeProductGrid(
    navController: NavController,
    viewModel: ProductListViewModel = koinViewModel<ProductListViewModel>()
) {
    val data by viewModel.state.collectAsState()

    val params = remember {
        ProductFormParamsEntity(
            categoryId = null,
            query = null,
            queryBy = null,
            sortBy = "created_at",
            sortOrder = "desc",
            limit = 4, // Limit to 4 items for home screen
            page = 1,
        )
    }

    LaunchedEffect(Unit) {
        viewModel.fetchProducts(params, isFirstLoad = true)
    }

    when (val state = data) {
        is ProductState.Success -> {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .height(500.dp) // Fixed height for home screen grid
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                userScrollEnabled = false // Disable grid scrolling
            ) {
                items(
                    count = state.data.take(4).size,
                    key = { index -> viewModel.getKeyForIndex(index) }
                ) { index ->
                    val item = state.data[index]
                    ProductCard(
                        itemEntity = item,
                        onClick = {
                            navController.navigate(AppScreen.ProductDetailScreen.route + "/${item.id}")
                        }
                    )
                }
            }
        }

        is ProductState.LoadingFirstPage -> {
            Box(
                modifier = Modifier
                    .height(400.dp)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        is ProductState.Error -> {
            Box(
                modifier = Modifier
                    .height(400.dp)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = state.message)
            }
        }

        else -> {}
    }
}