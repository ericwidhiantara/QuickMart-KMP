package com.luckyfrog.quickmart.features.home.presentation.dashboard.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.luckyfrog.quickmart.features.category.presentation.categories.CategoryListViewModel
import com.luckyfrog.quickmart.features.category.presentation.categories.CategoryState
import com.luckyfrog.quickmart.features.category.presentation.categories.component.CategoryCard
import com.luckyfrog.quickmart.utils.PageLoader
import com.luckyfrog.quickmart.utils.resource.route.AppScreen

@Composable
fun CategoryList(
    viewModel: CategoryListViewModel = hiltViewModel(),
    navController: NavController,
) {

    // Trigger the category fetch
    LaunchedEffect(Unit) {
        viewModel.fetchCategories()
    }

    // Observe the category from the ViewModel
    val data by viewModel.state.collectAsState()

    when (val state = data) {

        is CategoryState.Success -> {
            LazyRow(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth(),
            ) {
                val itemCount = state.data.data?.size ?: 0
                val displayCount =
                    itemCount.coerceAtMost(4)

                items(displayCount) { index ->
                    val item = state.data.data!![index]
                    CategoryCard(
                        itemEntity = item,
                        onClick = {
                            navController.navigate(
                                "${AppScreen.ProductListByCategoryScreen.route}?title=${item.name}&slug=beauty}"
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
