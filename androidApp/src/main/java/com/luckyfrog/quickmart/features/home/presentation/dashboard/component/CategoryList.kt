package com.luckyfrog.quickmart.features.home.presentation.dashboard.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
    val data by viewModel.data.collectAsState()
    when {
        data == null -> {
            // Loading or error state
            // Loading or error state with centered CircularProgressIndicator
            PageLoader(modifier = Modifier.height(60.dp))
        }

        else -> {

            // Category is loaded, show its details
            LazyRow(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth(),
            ) {
                val itemCount = data?.size ?: 0
                val displayCount =
                    itemCount.coerceAtMost(4)

                items(displayCount) { index ->
                    CategoryCard(
                        itemEntity = data!![index],
                        onClick = {
                            navController.navigate(
                                "${AppScreen.ProductListByCategoryScreen.route}?title=${data!![index].name}&slug=beauty}"
                            )
                        }
                    )
                }
                item { Spacer(modifier = Modifier.padding(4.dp)) }
            }
        }
    }

}
