package com.luckyfrog.quickmart.features.product.presentation.product_list.component

import android.widget.Toast
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.luckyfrog.quickmart.R
import com.luckyfrog.quickmart.features.product.domain.entities.ProductEntity
import com.luckyfrog.quickmart.features.wishlist.data.model.WishlistLocalItemDto
import com.luckyfrog.quickmart.features.wishlist.presentation.wishlist.WishlistViewModel
import kotlinx.coroutines.launch

@Composable
fun ProductCard(
    itemEntity: ProductEntity,
    onClick: () -> Unit,
    wishlistViewModel: WishlistViewModel = hiltViewModel()
) {

    LaunchedEffect(Unit) {
        wishlistViewModel.fetchWishlistItems()
    }

    val items by wishlistViewModel.wishlistItems.collectAsStateWithLifecycle()

    val isFavorite = items.any { it.id == itemEntity.id }

    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    Card(
        modifier = Modifier
            .padding(vertical = 8.dp)
            .clip(RoundedCornerShape(8.dp))
            .clickable { onClick() },
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        )
    ) {
        val painter = rememberAsyncImagePainter("https://picsum.photos/200/300")
        val transition by animateFloatAsState(
            targetValue = if (painter.state is AsyncImagePainter.State.Success) 1f else 0f,
            label = ""
        )
        Column {
            Box(
                modifier = Modifier
                    .height(150.dp)
                    .fillMaxWidth()
            ) {
                Image(
                    painter = painter,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(24.dp))
                        .alpha(transition)
                )
                IconButton(
                    modifier = Modifier
                        .padding(16.dp)
                        .size(24.dp) // Adjust the size to your preference
                        .clip(CircleShape) // Ensures the shape is circular
                        .background(MaterialTheme.colorScheme.onSecondary) // Background color
                        .align(Alignment.TopEnd),
                    onClick = {
                        val item = WishlistLocalItemDto(
                            id = itemEntity.id ?: "",
                            productName = itemEntity.name ?: "",
                            productPrice = itemEntity.price ?: 0.0,
                            discountPercentage = itemEntity.variants?.get(0)?.discountPercentage
                                ?: 0.0,
                            productImage = itemEntity.images?.get(0)
                                ?: "https://cdn.dummyjson.com/products/images/mens-watches/Brown%20Leather%20Belt%20Watch/1.png",
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
                        wishlistViewModel.fetchWishlistItems()

                    }
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
            Spacer(modifier = Modifier.padding(vertical = 8.dp))
            Text(
                text = itemEntity.name ?: "",
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.titleMedium,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(top = 8.dp)
            )

            Spacer(modifier = Modifier.padding(vertical = 4.dp))
            Text(
                text = itemEntity.localizedPrice ?: "",
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 8.dp),
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}