package com.luckyfrog.quickmart.features.product.presentation.product_list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.luckyfrog.quickmart.core.app.MainViewModel
import com.luckyfrog.quickmart.features.product.domain.entities.ProductEntity
import com.luckyfrog.quickmart.features.product.presentation.product_list.component.ProductCard
import com.luckyfrog.quickmart.utils.ErrorMessage
import com.luckyfrog.quickmart.utils.LoadingNextPageItem
import com.luckyfrog.quickmart.utils.NoData
import com.luckyfrog.quickmart.utils.PageLoader
import com.luckyfrog.quickmart.utils.resource.route.AppScreen

@Composable
fun ProductListScreen(
    mainViewModel: MainViewModel,
    navController: NavController,
    viewModel: ProductListViewModel = hiltViewModel(),
    isFromHomeScreen: Boolean = false
) {
    val productPagingItems: LazyPagingItems<ProductEntity> =
        viewModel.productsState.collectAsLazyPagingItems()

    Scaffold {
        productPagingItems.apply {
            when {
                loadState.refresh is LoadState.Loading -> {
                    PageLoader(modifier = Modifier.fillMaxSize())
                }

                loadState.refresh is LoadState.Error -> {
                    val error = productPagingItems.loadState.refresh as LoadState.Error
                    ErrorMessage(
                        modifier = Modifier,
                        message = error.error.localizedMessage!!,
                        onClickRetry = { retry() })
                }

                loadState.append is LoadState.Loading -> {
                    LoadingNextPageItem(modifier = Modifier.fillMaxSize())
                }

                loadState.append is LoadState.Error -> {
                    val error = productPagingItems.loadState.append as LoadState.Error
                    ErrorMessage(
                        modifier = Modifier,
                        message = error.error.localizedMessage!!,
                        onClickRetry = { retry() })
                }

                loadState.append is LoadState.NotLoading && productPagingItems.itemCount == 0 -> {
                    NoData(modifier = Modifier.fillMaxSize())
                }
            }
        }

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .padding(it),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            val itemCount = productPagingItems.itemCount
            val displayCount = if (isFromHomeScreen) itemCount.coerceAtMost(10) else itemCount

            items(displayCount) { index ->
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
