package com.luckyfrog.quickmart.features.category.presentation.categories

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.luckyfrog.quickmart.core.app.MainViewModel
import com.luckyfrog.quickmart.features.category.presentation.categories.component.CategoryCard

@Composable
fun CategoryListScreen(
    mainViewModel: MainViewModel,
    navController: NavController,
    viewModel: CategoryListViewModel = hiltViewModel(),
    isFromHomeScreen: Boolean = false
) {
    // Trigger the product detail fetch
    LaunchedEffect(Unit) {
        viewModel.fetchCategories()
    }

    // Observe the product details from the ViewModel
    val data by viewModel.data.collectAsState()
    
    Scaffold {
        when {
            data == null -> {
                // Loading or error state
                Text(
                    text = "Loading product details...",
                    color = MaterialTheme.colorScheme.onBackground
                )
            }

            else -> {
                // Product is loaded, show its details
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier.padding(it),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    val itemCount = data!!.size ?: 0
                    val displayCount =
                        if (isFromHomeScreen) itemCount.coerceAtMost(4) else itemCount

                    items(displayCount) { index ->
                        CategoryCard(
                            itemEntity = data!![index],
                            onClick = {
                            }
                        )
                    }
                    item { Spacer(modifier = Modifier.padding(4.dp)) }
                }
            }
        }


    }
}
