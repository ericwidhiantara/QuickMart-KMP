package com.luckyfrog.quickmart.features.product.presentation.product_list_by_category

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
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
import com.luckyfrog.quickmart.features.product.presentation.product_list.ProductListViewModel
import com.luckyfrog.quickmart.features.product.presentation.product_list.ProductState
import com.luckyfrog.quickmart.features.product.presentation.product_list.component.ProductCard
import com.luckyfrog.quickmart.utils.PageLoader
import com.luckyfrog.quickmart.utils.helper.Constants
import com.luckyfrog.quickmart.utils.helper.capitalizeWords
import com.luckyfrog.quickmart.utils.resource.route.AppScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductListByCategoryScreen(
    mainViewModel: MainViewModel,
    navController: NavController,
    viewModel: ProductListViewModel = hiltViewModel(),
    topBarTitle: String = stringResource(R.string.app_name),
    categoryId: String? = null // Receive the slug as a parameter
) {
    val data by viewModel.state.collectAsState()
    val sheetState = rememberModalBottomSheetState()
    var selectedFilter by remember { mutableStateOf("") }
    var showBottomSheet by remember { mutableStateOf(false) }

    val params = remember {
        ProductFormParamsEntity(
            categoryId = categoryId,
            query = null,
            queryBy = null,
            sortBy = "created_at",
            sortOrder = "asc",
            limit = Constants.MAX_PAGE_SIZE,
            page = 1,
        )
    }

    LaunchedEffect(Unit) {
        viewModel.fetchProducts(params, isFirstLoad = true)
    }
    Scaffold(
        topBar = {
            CustomTopBar(
                navController = navController,
                title = topBarTitle.capitalizeWords(),
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
    ) {
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

        when (val state = data) {
            is ProductState.Success -> {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier
                        .padding(it)
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    val itemCount = state.data.size

                    items(
                        count = itemCount,
                        key = { index -> viewModel.getKeyForIndex(index) }  // Use timestamp-based key
                    ) { index ->
                        val item = state.data[index]
                        ProductCard(
                            itemEntity = item,
                            onClick = {
                                navController.navigate("${AppScreen.ProductDetailScreen.route}/${item.id}")
                            }
                        )

                        // Check if we need to load more
                        if (index >= itemCount - 2 && !state.isLastPage && !state.isLoadingMore) {
                            LaunchedEffect(Unit) {
                                viewModel.fetchProducts(params)
                            }
                        }
                    }

                    // Show loading indicator only when actually loading more
                    if (state.isLoadingMore) {
                        item {
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
                PageLoader(modifier = Modifier.fillMaxSize())
            }

            is ProductState.Error -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = state.message)
                }
            }

            else -> {
                // Handle other states if needed
            }
        }

    }
}
