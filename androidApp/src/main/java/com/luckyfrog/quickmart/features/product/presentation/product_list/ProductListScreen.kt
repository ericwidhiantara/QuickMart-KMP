package com.luckyfrog.quickmart.features.product.presentation.product_list

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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductListScreen(
    mainViewModel: MainViewModel,
    navController: NavController,
    viewModel: ProductListViewModel = hiltViewModel(),
    isFromHomeScreen: Boolean = false,
    topBarTitle: String = stringResource(R.string.app_name)
) {
    val data by viewModel.state.collectAsState()
    val sheetState = rememberModalBottomSheetState()
    var selectedFilter by remember { mutableStateOf("") }

    var showBottomSheet by remember { mutableStateOf(false) }
    var page = remember { mutableIntStateOf(1) }
    LaunchedEffect(Unit) {
        val params = ProductFormParamsEntity(
            categoryId = null,
            query = null,
            queryBy = null,
            sortBy = "created_at",
            sortOrder = "asc",
            limit = Constants.MAX_PAGE_SIZE,
            page = page.intValue,
        )
        viewModel.fetchProducts(params)
    }

    Scaffold(

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
                        .padding(
                            horizontal = 16.dp
                        ),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    val itemCount = state.data.data?.size ?: 0
                    val displayCount =
                        if (isFromHomeScreen) itemCount.coerceAtMost(4) else itemCount

                    items(displayCount) { index ->
                        val item = state.data.data!![index]
                        ProductCard(
                            itemEntity = item,
                            onClick = {
                                val productId = item.id
                                navController.navigate("${AppScreen.ProductDetailScreen.route}/$productId")
                            }
                        )
                    }
                    item { Spacer(modifier = Modifier.padding(4.dp)) }
                }
            }

            is ProductState.Error -> {

            }

            is ProductState.Loading -> {
                PageLoader(modifier = Modifier.fillMaxSize())

            }

            is ProductState.Idle -> {

                // No action yet
            }
        }

    }
}
