package com.luckyfrog.quickmart.features.wishlist.presentation.wishlist

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.luckyfrog.quickmart.R
import com.luckyfrog.quickmart.core.widgets.CustomTopBar
import com.luckyfrog.quickmart.core.widgets.DeleteConfirmationDialog
import com.luckyfrog.quickmart.core.widgets.EmptyState
import com.luckyfrog.quickmart.features.general.presentation.main.NavBarViewModel
import com.luckyfrog.quickmart.features.wishlist.presentation.wishlist.component.WishlistItemCard
import kotlinx.coroutines.launch

@Composable
fun WishlistScreen(
    navController: NavController,
    navBarViewModel: NavBarViewModel = hiltViewModel(),
    viewModel: WishlistViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) {
        viewModel.fetchWishlistItems()
    }

    val items by viewModel.wishlistItems.collectAsStateWithLifecycle()

    val isWishlistEmpty = items.isEmpty()

    Scaffold(
        topBar = {
            CustomTopBar(
                title = stringResource(R.string.menu_wishlist),
                navController = navController,
                centeredTitle = true,
            )
        },

        ) { paddingValues ->
        if (isWishlistEmpty) {
            EmptyState(
                title = stringResource(id = R.string.empty_wishlist),
                description = stringResource(id = R.string.empty_wishlist_desc),
                buttonText = stringResource(id = R.string.explore_products),
                onButtonClick = {
                    navBarViewModel.updateIndex(1)
                }
            )
        } else {
            LazyColumn(
                modifier = Modifier.padding(paddingValues),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(items) { item ->
                    val context = LocalContext.current
                    val coroutineScope = rememberCoroutineScope()
                    var showDeleteDialog by remember { mutableStateOf(false) }

                    if (showDeleteDialog) {
                        DeleteConfirmationDialog(
                            onConfirm = {
                                viewModel.deleteItem(item)
                                coroutineScope.launch {
                                    viewModel.deleteItem(item)
                                    Toast.makeText(
                                        context,
                                        context.getString(R.string.removed_from_wishlist),
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                                showDeleteDialog = false
                            },
                            onDismiss = {
                                showDeleteDialog = false
                            }
                        )
                    }
                    WishlistItemCard(
                        imageUrl = item.productImage,
                        productName = item.productName,
                        currentPrice = item.productPrice - (item.productPrice * item.discountPercentage / 100),
                        originalPrice = item.productPrice,

                        onDelete = {
                            showDeleteDialog = true
                        }
                    )
                }
            }
        }

    }
}
