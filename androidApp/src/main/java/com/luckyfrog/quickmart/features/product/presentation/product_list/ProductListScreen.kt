package com.luckyfrog.quickmart.features.product.presentation.product_list

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.luckyfrog.quickmart.R
import com.luckyfrog.quickmart.core.app.MainViewModel
import com.luckyfrog.quickmart.core.resources.Images
import com.luckyfrog.quickmart.core.widgets.CustomFilterBottomSheet
import com.luckyfrog.quickmart.core.widgets.CustomTopBar
import com.luckyfrog.quickmart.features.product.domain.entities.ProductFormParamsEntity
import com.luckyfrog.quickmart.features.product.presentation.product_list.component.ProductCard
import com.luckyfrog.quickmart.utils.PageLoader
import com.luckyfrog.quickmart.utils.helper.Constants
import com.luckyfrog.quickmart.utils.resource.route.AppScreen

// ProductListScreen.kt
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductListScreen(
    mainViewModel: MainViewModel,
    navController: NavController,
    viewModel: ProductListViewModel = hiltViewModel(),
    isFromHomeScreen: Boolean = false,
    topBarTitle: String = stringResource(R.string.app_name),
    modifier: Modifier = Modifier
) {
    val data by viewModel.state.collectAsState()
    val sheetState = rememberModalBottomSheetState()
    var selectedFilter by remember { mutableStateOf("") }
    var showBottomSheet by remember { mutableStateOf(false) }

    val params = remember {
        ProductFormParamsEntity(
            categoryId = null,
            query = null,
            queryBy = null,
            sortBy = "created_at",
            sortOrder = "desc",
            limit = if (isFromHomeScreen) 4 else Constants.MAX_PAGE_SIZE,
            page = 1,
        )
    }

    LaunchedEffect(Unit) {
        viewModel.fetchProducts(params, isFirstLoad = true)
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            if (!isFromHomeScreen) {
                CustomTopBar(
                    navController = navController,
                    title = topBarTitle,
                    centeredTitle = false,
                    actions = {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            content = {
                                IconButton(onClick = {
                                    showBottomSheet = true
                                }) {
                                    Image(
                                        painter = painterResource(Images.icFilter),
                                        contentDescription = "Filter",
                                        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onBackground)
                                    )
                                }

                                IconButton(onClick = {
                                    navController.navigate(AppScreen.SearchScreen.route)
                                }) {
                                    Image(
                                        painter = painterResource(Images.icSearch),
                                        contentDescription = "Search",
                                        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onBackground)
                                    )
                                }
                            }
                        )
                    },
                )
            }
        }
    ) { innerPadding ->
        if (showBottomSheet) {
            ModalBottomSheet(
                containerColor = MaterialTheme.colorScheme.background,
                onDismissRequest = {
                    showBottomSheet = false
                },
                sheetState = sheetState
            ) {
                CustomFilterBottomSheet(
                    selectedFilter = selectedFilter,
                    onFilterSelected = { value ->
                        selectedFilter = value
                    },
                    onApply = {
                        showBottomSheet = false
                    }
                )
            }
        }

        ProductGrid(
            productState = data,
            viewModel = viewModel,
            navController = navController,
            isFromHomeScreen = isFromHomeScreen,
            params = params,
            modifier = Modifier
                .padding(innerPadding)
                .then(
                    if (isFromHomeScreen) {
                        Modifier
                            .height(500.dp)
                            .padding(horizontal = 16.dp)
                    } else {
                        Modifier
                            .fillMaxSize()
                            .padding(horizontal = 16.dp)
                    }
                )
        )
    }
}

@Composable
private fun ProductGrid(
    productState: ProductState,
    viewModel: ProductListViewModel,
    navController: NavController,
    isFromHomeScreen: Boolean,
    params: ProductFormParamsEntity,
    modifier: Modifier = Modifier
) {
    when (productState) {
        is ProductState.Success -> {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = modifier,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                userScrollEnabled = !isFromHomeScreen // Enable scrolling only for full list
            ) {
                val itemCount = if (isFromHomeScreen) {
                    productState.data.size.coerceAtMost(4)
                } else {
                    productState.data.size
                }

                items(
                    count = itemCount,
                    key = { index -> viewModel.getKeyForIndex(index) }
                ) { index ->
                    val item = productState.data[index]
                    ProductCard(
                        itemEntity = item,
                        onClick = {
                            navController.navigate(AppScreen.ProductDetailScreen.route + "/${item.id}")
                        }
                    )

                    // Check if we need to load more
                    if (!isFromHomeScreen &&
                        index >= itemCount - 2 &&
                        !productState.isLastPage &&
                        !productState.isLoadingMore
                    ) {
                        LaunchedEffect(Unit) {
                            viewModel.fetchProducts(params)
                        }
                    }
                }

                // Show loading indicator only when loading more in full list
                if (productState.isLoadingMore && !isFromHomeScreen) {
                    item(span = { GridItemSpan(2) }) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }
                }
            }
        }

        is ProductState.LoadingFirstPage -> {
            Box(
                modifier = modifier,
                contentAlignment = Alignment.Center
            ) {
                PageLoader()
            }
        }

        is ProductState.Error -> {
            Box(
                modifier = modifier,
                contentAlignment = Alignment.Center
            ) {
                Text(text = productState.message)
            }
        }

        else -> {
            // Handle other states if needed
        }
    }
}