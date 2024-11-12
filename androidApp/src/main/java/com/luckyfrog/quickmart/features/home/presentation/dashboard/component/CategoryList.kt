package com.luckyfrog.quickmart.features.home.presentation.dashboard.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.luckyfrog.quickmart.features.category.domain.entities.CategoryFormParamsEntity
import com.luckyfrog.quickmart.features.category.presentation.categories.CategoryListViewModel
import com.luckyfrog.quickmart.features.category.presentation.categories.CategoryState
import com.luckyfrog.quickmart.features.category.presentation.categories.component.CategoryCard
import com.luckyfrog.quickmart.utils.PageLoader
import com.luckyfrog.quickmart.utils.helper.Constants
import com.luckyfrog.quickmart.utils.resource.route.AppScreen

@Composable
fun CategoryList(
    viewModel: CategoryListViewModel = hiltViewModel(),
    navController: NavController,
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

    when (val state = data) {
        is CategoryState.Success -> {
            LazyRow(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth(),
            ) {
                val itemCount =
                    state.data.size.coerceAtMost(4)

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
