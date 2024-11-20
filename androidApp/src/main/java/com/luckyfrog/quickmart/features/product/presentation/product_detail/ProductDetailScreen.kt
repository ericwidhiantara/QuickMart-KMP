package com.luckyfrog.quickmart.features.product.presentation.product_detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.luckyfrog.quickmart.R
import com.luckyfrog.quickmart.core.resources.Images
import com.luckyfrog.quickmart.core.widgets.CustomOutlinedButton
import com.luckyfrog.quickmart.core.widgets.PagerIndicator
import com.luckyfrog.quickmart.features.product.domain.entities.ProductEntity
import com.luckyfrog.quickmart.utils.PageLoader
import com.luckyfrog.quickmart.utils.helper.capitalizeWords
import com.luckyfrog.quickmart.utils.resource.theme.colorBlue
import com.luckyfrog.quickmart.utils.resource.theme.colorCyan
import com.luckyfrog.quickmart.utils.resource.theme.colorOrange
import com.luckyfrog.quickmart.utils.resource.theme.colorWhite
import kotlinx.coroutines.delay

@Composable
fun ProductDetailScreen(
    viewModel: ProductDetailViewModel = hiltViewModel(),
    productId: String,
    navController: NavController
) {
    val data by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchProductDetail(productId)
    }

    Scaffold(
        content = { paddingValues ->
            when (val state = data) {
                is ProductDetailState.Success -> ProductDetailContent(
                    paddingValues = paddingValues,
                    navController = navController,
                    product = state.data
                )

                is ProductDetailState.Loading -> {
                    PageLoader(modifier = Modifier.fillMaxSize())
                }

                is ProductDetailState.Error -> {
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
        },
        bottomBar = {
            ProductDetailBottomSection()
        }
    )
}

@Composable
fun ProductDetailContent(
    paddingValues: PaddingValues,
    navController: NavController,
    product: ProductEntity,
) {
    val scrollState = rememberScrollState()

    val scrollOffset by remember {
        derivedStateOf {
            val maxScroll = 800f
            val currentScroll = scrollState.value.toFloat()
            (1f - (currentScroll / maxScroll)).coerceIn(0f, 1f)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)

    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
        ) {
            // Sticky header
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp * scrollOffset)
                    .background(MaterialTheme.colorScheme.background)
            ) {
                ProductHeaderSection()
            }

            // Content section
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        top = 275.dp
                    )
                    .clip(RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp))
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(horizontal = 16.dp, vertical = 24.dp)

            ) {
                // Tags section
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(
                        8.dp,
                        Alignment.CenterHorizontally
                    ),
                ) {
                    items(product.tags?.size ?: 0) { index ->
                        val colors = listOf(colorCyan, colorBlue, colorOrange)
                        val randomColor = colors[index % colors.size]
                        Box(
                            modifier = Modifier
                                .background(
                                    color = randomColor,
                                    shape = RoundedCornerShape(12.dp)
                                )
                        ) {
                            Text(
                                text = product.tags?.get(index)?.capitalizeWords() ?: "",
                                color = colorWhite,
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Product info
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = product.name?.capitalizeWords() ?: "",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                    )
                    Text(
                        text = product.variants?.get(0)?.localizedPrice ?: "",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Description
                Text(
                    text = product.description ?: "",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                )

                // Add more content as needed
                Spacer(modifier = Modifier.height(100.dp))
            }

        }
// Top bar with back button and favorite
        TopAppBar(
            modifier = Modifier.align(Alignment.TopStart),
            scrollOffset = scrollOffset,
            title = product.name?.capitalizeWords() ?: "",
            onBackClick = { navController.popBackStack() },
            onFavoriteClick = { }
        )

    }
}

@Composable
private fun TopAppBar(
    modifier: Modifier = Modifier,
    scrollOffset: Float,
    title: String,
    onBackClick: () -> Unit,
    onFavoriteClick: () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(
                MaterialTheme.colorScheme.surface.copy(
                    alpha = (1f - scrollOffset).coerceIn(0f, 1f)
                )
            )
            .padding(vertical = 12.dp)

    ) {
        // Back button
        IconButton(
            onClick = onBackClick,
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(horizontal = 16.dp)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = stringResource(id = Images.icArrowBack),
                tint = MaterialTheme.colorScheme.onBackground
            )
        }

        // Title - only visible when collapsed
        if (scrollOffset < 0.5f) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(horizontal = 56.dp)
            )
        }

        // Favorite button
        IconButton(
            modifier = Modifier
                .padding(8.dp)
                .padding(horizontal = 16.dp)
                .size(32.dp) // Adjust the size to your preference
                .clip(CircleShape) // Ensures the shape is circular
                .background(MaterialTheme.colorScheme.onSecondary) // Background color
                .align(Alignment.TopEnd),
            onClick = onFavoriteClick
        ) {
            Icon(
                Icons.Default.FavoriteBorder,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primaryContainer
            )
        }

    }
}

@Composable
fun ProductHeaderSection(
) {
    val images = listOf(
        Images.icProductDummy,
        Images.icProductDummy,
        Images.icProductDummy,
        Images.icProductDummy,
    )
    val pagerState = rememberPagerState(pageCount = { images.size })

    LaunchedEffect(Unit) {
        while (true) {
            delay(5000)
            val nextPage = (pagerState.currentPage + 1) % pagerState.pageCount
            pagerState.scrollToPage(nextPage)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { currentPage ->

            Image(
                painter = painterResource(id = images[currentPage]),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

        }

        // Page indicator
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(
                    bottom = 40.dp
                ),
            content = {
                PagerIndicator(
                    arraySize = pagerState.pageCount,
                    currentIndex = pagerState.currentPage
                )
            },
        )


    }
}

@Composable
fun ProductDetailBottomSection() {
    // Get the screen height from LocalConfiguration
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 20.dp),
        horizontalArrangement = Arrangement.SpaceBetween,

        content = {
            CustomOutlinedButton(
                buttonText = stringResource(R.string.buy_now),
                buttonTextColor = if (isSystemInDarkTheme()) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onPrimaryContainer,
                modifier = Modifier.width(screenWidth * 0.45F),
                onClick = {


                },
                buttonBorderColor = MaterialTheme.colorScheme.onPrimaryContainer,
                buttonContainerColor = if (isSystemInDarkTheme()) MaterialTheme.colorScheme.onPrimaryContainer else Color.Transparent,

                )
            CustomOutlinedButton(
                buttonText = stringResource(R.string.add_to_cart),
                modifier = Modifier.width(screenWidth * 0.45F),
                isWithIcon = true,
                buttonIcon = painterResource(Images.icShoppingCart),
                onClick = {

                },
                buttonContainerColor = if (isSystemInDarkTheme()) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onPrimaryContainer,
            )

        }
    )
}