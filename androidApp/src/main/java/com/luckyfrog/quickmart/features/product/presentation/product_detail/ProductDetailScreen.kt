package com.luckyfrog.quickmart.features.product.presentation.product_detail

import android.widget.Toast
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
import androidx.compose.material.icons.filled.Favorite
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.luckyfrog.quickmart.R
import com.luckyfrog.quickmart.core.resources.Images
import com.luckyfrog.quickmart.core.widgets.CustomOutlinedButton
import com.luckyfrog.quickmart.core.widgets.ExpandableText
import com.luckyfrog.quickmart.core.widgets.PagerIndicator
import com.luckyfrog.quickmart.features.cart.data.model.CartLocalItemDto
import com.luckyfrog.quickmart.features.cart.presentation.my_cart.CartViewModel
import com.luckyfrog.quickmart.features.product.domain.entities.ProductEntity
import com.luckyfrog.quickmart.features.profile.presentation.profile.UserState
import com.luckyfrog.quickmart.features.profile.presentation.profile.UserViewModel
import com.luckyfrog.quickmart.features.wishlist.data.model.WishlistLocalItemDto
import com.luckyfrog.quickmart.features.wishlist.presentation.wishlist.WishlistViewModel
import com.luckyfrog.quickmart.utils.PageLoader
import com.luckyfrog.quickmart.utils.helper.capitalizeWords
import com.luckyfrog.quickmart.utils.resource.theme.colorBlue
import com.luckyfrog.quickmart.utils.resource.theme.colorCyan
import com.luckyfrog.quickmart.utils.resource.theme.colorOrange
import com.luckyfrog.quickmart.utils.resource.theme.colorWhite
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun ProductDetailScreen(
    viewModel: ProductDetailViewModel = hiltViewModel(),
    productId: String,
    navController: NavController,
    userViewModel: UserViewModel = hiltViewModel(),
) {
    val data by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchProductDetail(productId)
        userViewModel.getUserLogin()
    }
    var userId by remember { mutableStateOf("") }
    when (val userState = userViewModel.userState.collectAsState().value) {
        is UserState.Success -> {
            userId = userState.data.id
        }

        is UserState.Error -> {}
        UserState.Idle -> {}
        UserState.Loading -> {}
    }
    when (val state = data) {
        is ProductDetailState.Success -> {
            Scaffold(
                bottomBar = {
                    ProductDetailBottomSection(state.data, userId = userId)
                }
            ) { paddingValues ->
                ProductDetailContent(
                    paddingValues = paddingValues,
                    navController = navController,
                    product = state.data,
                    userId = userId
                )
            }
        }

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


}

@Composable
fun ProductDetailContent(
    paddingValues: PaddingValues,
    navController: NavController,
    product: ProductEntity,
    wishlistViewModel: WishlistViewModel = hiltViewModel(),
    userId: String,
) {

    LaunchedEffect(Unit) {
        wishlistViewModel.fetchWishlistItems(userId)
    }
    val scrollState = rememberScrollState()

    val items by wishlistViewModel.wishlistItems.collectAsStateWithLifecycle()

    val isFavorite = items.any { it.id == product.id }

    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

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
                ExpandableText(
                    text = product.description ?: "",
                    maxLines = 4
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
            isFavorite = isFavorite,
            onFavoriteClick = {
                val item = WishlistLocalItemDto(
                    id = product.id ?: "",
                    productName = product.name ?: "",
                    productPrice = product.variants?.get(0)?.price ?: 0.0,
                    discountPercentage = product.variants?.get(0)?.discountPercentage ?: 0.0,
                    productImage = product.images?.get(0)
                        ?: "https://cdn.dummyjson.com/products/images/mens-watches/Brown%20Leather%20Belt%20Watch/1.png",
                    userId = userId,
                )
                if (isFavorite) {
                    coroutineScope.launch {
                        wishlistViewModel.deleteItem(item)
                        Toast.makeText(
                            context,
                            context.getString(R.string.removed_from_wishlist),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    coroutineScope.launch {
                        wishlistViewModel.addItem(item)
                        Toast.makeText(
                            context,
                            context.getString(R.string.added_to_wishlist),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
                wishlistViewModel.fetchWishlistItems(userId)

            }
        )

    }
}

@Composable
private fun TopAppBar(
    modifier: Modifier = Modifier,
    scrollOffset: Float,
    title: String,
    onBackClick: () -> Unit,
    onFavoriteClick: () -> Unit,
    isFavorite: Boolean = false,

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
                imageVector = if (isFavorite) {
                    Icons.Filled.Favorite
                } else {
                    Icons.Filled.FavoriteBorder
                },
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
        "https://cdn.dummyjson.com/products/images/mens-watches/Brown%20Leather%20Belt%20Watch/1.png",
        "https://cdn.dummyjson.com/products/images/mens-watches/Brown%20Leather%20Belt%20Watch/2.png",
        "https://cdn.dummyjson.com/products/images/mens-watches/Brown%20Leather%20Belt%20Watch/3.png",
        "https://cdn.dummyjson.com/products/images/mens-watches/Longines%20Master%20Collection/1.png",
        "https://cdn.dummyjson.com/products/images/mens-watches/Longines%20Master%20Collection/2.png",
        "https://cdn.dummyjson.com/products/images/mens-watches/Longines%20Master%20Collection/3.png",
        "https://cdn.dummyjson.com/products/images/mens-watches/Rolex%20Cellini%20Date%20Black%20Dial/1.png",
        "https://cdn.dummyjson.com/products/images/mens-watches/Rolex%20Cellini%20Date%20Black%20Dial/2.png",
        "https://cdn.dummyjson.com/products/images/mens-watches/Rolex%20Cellini%20Date%20Black%20Dial/3.png"
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
            AsyncImage(
                model = images[currentPage],
                contentDescription = "image",
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
fun ProductDetailBottomSection(
    product: ProductEntity,
    cartModel: CartViewModel = hiltViewModel(),
    userId: String,
) {
    // Get the screen height from LocalConfiguration
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        cartModel.fetchCartItems(userId)
    }

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
                isWithIcon = false,
                buttonIcon = painterResource(Images.icShoppingCart),
                onClick = {
                    val item = CartLocalItemDto(
                        id = product.id ?: "",
                        productName = product.name ?: "",
                        productPrice = product.variants?.get(0)?.price ?: 0.0,
                        discountPercentage = product.variants?.get(0)?.discountPercentage ?: 0.0,
                        qty = 1,
                        selected = true,
                        productImage = product.images?.get(0)
                            ?: "https://cdn.dummyjson.com/products/images/mens-watches/Brown%20Leather%20Belt%20Watch/1.png",
                        userId = userId
                    )
                    coroutineScope.launch {
                        cartModel.addItem(item)

                        Toast.makeText(
                            context,
                            context.getString(R.string.added_to_cart),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                },
                buttonContainerColor = if (isSystemInDarkTheme()) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onPrimaryContainer,
            )

        }
    )
}