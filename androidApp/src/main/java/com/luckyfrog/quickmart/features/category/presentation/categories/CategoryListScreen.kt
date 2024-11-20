package com.luckyfrog.quickmart.features.category.presentation.categories

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.luckyfrog.quickmart.R
import com.luckyfrog.quickmart.core.app.MainViewModel
import com.luckyfrog.quickmart.core.widgets.CustomTopBar
import com.luckyfrog.quickmart.features.category.domain.entities.CategoryFormParamsEntity
import com.luckyfrog.quickmart.features.category.presentation.categories.component.CategoryCard
import com.luckyfrog.quickmart.utils.PageLoader
import com.luckyfrog.quickmart.utils.helper.Constants
import com.luckyfrog.quickmart.utils.resource.route.AppScreen

@Composable
fun CategoryListScreen(
    mainViewModel: MainViewModel,
    navController: NavController,
    viewModel: CategoryListViewModel = hiltViewModel(),
    isFromHomeScreen: Boolean = false
) {
    val params = remember {
        CategoryFormParamsEntity(
            query = null,
            queryBy = null,
            sortBy = "created_at",
            sortOrder = "desc",
            limit = Constants.MAX_PAGE_SIZE,
            page = 1,
        )
    }

    LaunchedEffect(Unit) {
        viewModel.fetchCategories(params, isFirstLoad = true)
    }
    val data by viewModel.state.collectAsState()


    Scaffold(
        topBar = {
            CustomTopBar(
                title = stringResource(R.string.menu_categories),
                navController = navController,
                centeredTitle = true,
            )
        },
    ) {
        when (val state = data) {
            is CategoryState.Success -> {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier
                        .padding(it)
                        .padding(horizontal = 16.dp, vertical = 20.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    val itemCount = if (isFromHomeScreen) {
                        state.data.size.coerceAtMost(4)
                    } else {
                        state.data.size
                    }

                    items(
                        count = itemCount,
                        key = { index -> viewModel.getKeyForIndex(index) }  // Use timestamp-based key
                    ) { index ->
                        val item = state.data[index]
                        CategoryCard(
                            itemEntity = item,
                            onClick = {
                                navController.navigate(
                                    "${AppScreen.ProductListByCategoryScreen.route}?id=${item.id}&title=${item.name}"
                                )
                            }
                        )

                        // Check if we need to load more
                        if (index >= itemCount - 2 && !state.isLastPage && !isFromHomeScreen && !state.isLoadingMore) {
                            LaunchedEffect(Unit) {
                                viewModel.fetchCategories(params)
                            }
                        }
                    }

                    // Show loading indicator only when actually loading more
                    if (state.isLoadingMore && !isFromHomeScreen) {
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

            is CategoryState.LoadingFirstPage -> {
                PageLoader(modifier = Modifier.fillMaxSize())
            }

            is CategoryState.Error -> {
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
