package com.luckyfrog.quickmart.features.product.presentation.product_list_by_category

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.luckyfrog.quickmart.R
import com.luckyfrog.quickmart.core.app.MainViewModel
import com.luckyfrog.quickmart.core.resources.Images
import com.luckyfrog.quickmart.core.widgets.CustomFilterBottomSheet
import com.luckyfrog.quickmart.core.widgets.CustomTopBar
import com.luckyfrog.quickmart.features.product.domain.entities.ProductEntity
import com.luckyfrog.quickmart.features.product.domain.entities.ProductFormParamsEntity
import com.luckyfrog.quickmart.features.product.presentation.product_list.component.ProductCard
import com.luckyfrog.quickmart.utils.ErrorMessage
import com.luckyfrog.quickmart.utils.LoadingNextPageItem
import com.luckyfrog.quickmart.utils.NoData
import com.luckyfrog.quickmart.utils.PageLoader
import com.luckyfrog.quickmart.utils.helper.Constants
import com.luckyfrog.quickmart.utils.resource.route.AppScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductListByCategoryScreen(
    mainViewModel: MainViewModel,
    navController: NavController,
    viewModel: ProductListByCategoryViewModel = hiltViewModel(),
    topBarTitle: String = stringResource(R.string.app_name),
    categorySlug: String? = null // Receive the slug as a parameter
) {
    // Call onEvent with the category slug to fetch products
    if (categorySlug != null) {
        val params = ProductFormParamsEntity(
            limit = Constants.MAX_PAGE_SIZE,
            skip = 0,
            category = categorySlug,
            order = "asc",
            sortBy = "createdAt",
            q = null
        )
        viewModel.onEvent(ProductListByCategory.GetProductsByCategory(params))
    }

    val productPagingItems: LazyPagingItems<ProductEntity> =
        viewModel.productsState.collectAsLazyPagingItems()

    val sheetState = rememberModalBottomSheetState()
    var selectedFilter by remember { mutableStateOf("") }

    var showBottomSheet by remember { mutableStateOf(false) }
    Scaffold(
        topBar = {
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

        productPagingItems.apply {
            when {
                loadState.refresh is LoadState.Loading -> {
                    PageLoader(modifier = Modifier.fillMaxSize())
                }

                loadState.refresh is LoadState.Error -> {
                    val error = productPagingItems.loadState.refresh as LoadState.Error
                    ErrorMessage(
                        modifier = Modifier,
                        message = error.error.localizedMessage ?: "Unknown Error",
                        onClickRetry = { retry() }
                    )
                }

                loadState.append is LoadState.Loading -> {
                    LoadingNextPageItem(modifier = Modifier.fillMaxSize())
                }

                loadState.append is LoadState.Error -> {
                    val error = productPagingItems.loadState.append as LoadState.Error
                    ErrorMessage(
                        modifier = Modifier,
                        message = error.error.localizedMessage ?: "Unknown Error",
                        onClickRetry = { retry() }
                    )
                }

                loadState.append is LoadState.NotLoading && productPagingItems.itemCount == 0 -> {
                    NoData(modifier = Modifier.fillMaxSize())
                }
            }
        }

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.padding(it),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            val itemCount = productPagingItems.itemCount

            items(itemCount) { index ->
                productPagingItems[index]?.let { product ->
                    ProductCard(
                        itemEntity = product,
                        onClick = {
                            val productId = product.id
                            navController.navigate("${AppScreen.ProductDetailScreen.route}/$productId")
                        }
                    )
                }
            }
            item { Spacer(modifier = Modifier.padding(4.dp)) }
        }
    }
}
