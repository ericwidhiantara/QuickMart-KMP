package com.luckyfrog.quickmart.features.category.presentation.categories

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.luckyfrog.quickmart.R
import com.luckyfrog.quickmart.core.app.MainViewModel
import com.luckyfrog.quickmart.core.widgets.CustomTopBar
import com.luckyfrog.quickmart.features.category.presentation.categories.component.CategoryCard
import com.luckyfrog.quickmart.utils.PageLoader
import com.luckyfrog.quickmart.utils.resource.route.AppScreen

@Composable
fun CategoryListScreen(
    mainViewModel: MainViewModel,
    navController: NavController,
    viewModel: CategoryListViewModel = hiltViewModel(),
    isFromHomeScreen: Boolean = false
) {
    // Trigger the category fetch
    LaunchedEffect(Unit) {
        viewModel.fetchCategories()
    }

    // Observe the category from the ViewModel
    val data by viewModel.state.collectAsState()

    Scaffold(
        topBar = {
            CustomTopBar(
                title = stringResource(R.string.menu_categories),
                navController = navController,
                centeredTitle = true
            )
        },
    ) {
        when (val state = data) {

            is CategoryState.Success -> {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier
                        .padding(it)
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    val itemCount = state.data.data?.size ?: 0
                    val displayCount =
                        if (isFromHomeScreen) itemCount.coerceAtMost(4) else itemCount

                    items(displayCount) { index ->
                        val item = state.data.data!![index]
                        CategoryCard(
                            itemEntity = item,
                            onClick = {
                                navController.navigate(
                                    "${AppScreen.ProductListByCategoryScreen.route}?id=${item.id}&title=${item.name}"
                                )
                            }
                        )
                    }
                    item { Spacer(modifier = Modifier.padding(4.dp)) }
                }
            }

            is CategoryState.Error -> {

            }

            is CategoryState.Loading -> {
                PageLoader(modifier = Modifier.fillMaxSize())

            }

            is CategoryState.Idle -> {

                // No action yet
            }
        }
    }
}
