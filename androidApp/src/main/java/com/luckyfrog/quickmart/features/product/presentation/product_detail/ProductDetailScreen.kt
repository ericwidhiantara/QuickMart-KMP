package com.luckyfrog.quickmart.features.product.presentation.product_detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.luckyfrog.quickmart.core.widgets.CustomTopBar
import com.luckyfrog.quickmart.features.product.domain.entities.ProductEntity
import kotlin.math.min

@Composable
fun ProductDetailScreen(
    viewModel: ProductDetailViewModel = hiltViewModel(),
    productId: Int, // Pass productId to fetch the details
    navController: NavController,
) {

    Scaffold(
        topBar = {
            CustomTopBar(
                title = "Product Detail",
                navController = navController
            )
        },
        content = { paddingValues ->
            // Trigger the product detail fetch
            LaunchedEffect(productId) {
                viewModel.fetchProductDetail(productId)
            }

            // Observe the product details from the ViewModel
            val productDetail by viewModel.productDetail.collectAsState()

            // Display the product details
            Column(
                modifier = Modifier.padding(paddingValues)
            ) {

                when {
                    productDetail == null -> {
                        // Loading or error state
                        Text(
                            text = "Loading product details...",
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }

                    else -> {
                        // Product is loaded, show its details
                        productDetail?.let { product ->
                            ProductDetailContent(product = product)
                        }
                    }
                }
            }
        }
    )

//    CollapsingProductDetailScreen(onBackClick = { /*TODO*/ }, onFavoriteClick = { /*TODO*/ })
}

@Composable
fun ProductDetailContent(product: ProductEntity) {
    Column {
        Text(
            text = "Product Name: ${product.name}",
            style = MaterialTheme.typography.headlineMedium
        )
        Text(text = "Product Price: ${product.price}", style = MaterialTheme.typography.bodyLarge)
        Text(
            text = "Product Description: ${product.description}",
            style = MaterialTheme.typography.bodySmall
        )
        // Add more fields as needed
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CollapsingProductDetailScreen(
    onBackClick: () -> Unit,
    onFavoriteClick: () -> Unit,
    images: List<String> = listOf(
        "https://via.placeholder.com/360x290",
        "https://via.placeholder.com/360x290",
        "https://via.placeholder.com/360x290"
    )
) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    val toolbarState = scrollBehavior.state

    // Define heights
    val maxImageHeight = 350.dp
    val minImageHeight = 56.dp

    // Convert to pixels for calculation
    val maxHeightPx = with(LocalDensity.current) { maxImageHeight.toPx() }
    val minHeightPx = with(LocalDensity.current) { minImageHeight.toPx() }

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent,
                    navigationIconContentColor = Color.Black,
                    actionIconContentColor = Color.Black
                ),
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, "Back")
                    }
                },
                actions = {
                    IconButton(onClick = onFavoriteClick) {
                        Icon(Icons.Default.FavoriteBorder, "Favorite")
                    }
                },
                title = {},
                scrollBehavior = scrollBehavior
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                item {
                    // Image Carousel with collapsing effect
                    val pagerState = rememberPagerState(pageCount = { images.size })
                    val heightProgress = if (toolbarState.overlappedFraction < 0) {
                        0f
                    } else {
                        min(1f, toolbarState.overlappedFraction)
                    }

                    val imageHeight = lerp(
                        maxHeightPx,
                        minHeightPx,
                        heightProgress
                    ).dp

                    Box {
                        HorizontalPager(
                            state = pagerState,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(imageHeight)
                                .graphicsLayer {
                                    alpha = 1f - heightProgress
                                }
                        ) { page ->
                            AsyncImage(
                                model = images[page],
                                contentDescription = "Product image ${page + 1}",
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )
                        }

                        // Page Indicator
                        if (heightProgress < 0.5f) {
                            Row(
                                Modifier
                                    .height(50.dp)
                                    .fillMaxWidth()
                                    .align(Alignment.BottomCenter),
                                horizontalArrangement = Arrangement.Center
                            ) {
                                repeat(images.size) { iteration ->
                                    val color = if (pagerState.currentPage == iteration) {
                                        MaterialTheme.colorScheme.primary
                                    } else {
                                        Color.LightGray
                                    }
                                    Box(
                                        modifier = Modifier
                                            .padding(2.dp)
                                            .size(8.dp)
                                            .background(
                                                color,
                                                shape = MaterialTheme.shapes.medium
                                            )
                                    )
                                }
                            }
                        }
                    }
                }

                // Product Details
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Loop Silicone Strong\nMagnetic Watch",
                                style = MaterialTheme.typography.headlineSmall
                            )
                            Column(horizontalAlignment = Alignment.End) {
                                Text(
                                    text = "$15.25",
                                    style = MaterialTheme.typography.headlineSmall
                                )
                                Text(
                                    text = "$20.00",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = Color.Gray
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "4.5",
                                style = MaterialTheme.typography.bodyLarge
                            )
                            Text(
                                text = " (495 reviews)",
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.Gray
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = "Constructed with high-quality silicone material, " +
                                    "the Loop Silicone Strong Magnetic Watch ensures a " +
                                    "comfortable and secure fit on your wrist. The soft " +
                                    "and flexible silicone is gentle on the skin, making " +
                                    "it ideal for extended wear.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Gray
                        )

                        // Add more content to make it scrollable
                        repeat(5) {
                            Spacer(modifier = Modifier.height(32.dp))
                            Text(
                                text = "Additional product details section ${it + 1}",
                                style = MaterialTheme.typography.bodyLarge
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Lorem ipsum dolor sit amet, consectetur " +
                                        "adipiscing elit. Sed do eiusmod tempor " +
                                        "incididunt ut labore et dolore magna aliqua.",
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.Gray
                            )
                        }
                    }
                }
            }

            // Bottom Buttons
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Button(
                        onClick = { /* Buy Now */ },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Buy Now")
                    }
                    Button(
                        onClick = { /* Add to Cart */ },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Add to Cart")
                    }
                }
            }
        }
    }
}

private fun lerp(start: Float, end: Float, fraction: Float): Float {
    return start + (end - start) * fraction
}