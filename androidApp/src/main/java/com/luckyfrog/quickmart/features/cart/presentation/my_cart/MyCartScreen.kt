package com.luckyfrog.quickmart.features.cart.presentation.my_cart

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.luckyfrog.quickmart.R
import com.luckyfrog.quickmart.core.widgets.CustomTopBar
import com.luckyfrog.quickmart.core.widgets.EmptyState
import com.luckyfrog.quickmart.features.cart.presentation.my_cart.component.CartItemCard
import com.luckyfrog.quickmart.features.cart.presentation.my_cart.component.CartSummaryBar
import com.luckyfrog.quickmart.features.cart.presentation.my_cart.component.VoucherCodeBottomSheet

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyCartScreen(
    navController: NavController,
) {
    var cartItems by remember {
        mutableStateOf(
            listOf(
                CartItem(
                    id = 1,
                    name = "Loop Silicone Strong Magnetic Watch",
                    imageUrl = "https://cdn.dummyjson.com/products/images/mens-watches/Brown%20Leather%20Belt%20Watch/1.png",
                    currentPrice = 15.25,
                    originalPrice = 20.00,
                    quantity = 1,
                    isChecked = false
                ),

                CartItem(
                    id = 1,
                    name = "Loop Silicone Strong Magnetic Watch",
                    imageUrl = "https://cdn.dummyjson.com/products/images/mens-watches/Brown%20Leather%20Belt%20Watch/1.png",
                    currentPrice = 15.25,
                    originalPrice = 20.00,
                    quantity = 1,
                    isChecked = false
                ),
                CartItem(
                    id = 1,
                    name = "Loop Silicone Strong Magnetic Watch",
                    imageUrl = "https://cdn.dummyjson.com/products/images/mens-watches/Brown%20Leather%20Belt%20Watch/1.png",
                    currentPrice = 15.25,
                    originalPrice = 20.00,
                    quantity = 1,
                    isChecked = false
                )
            )
        )
    }

    var selectedVoucher by remember { mutableStateOf("") }
    var showBottomSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()
    val isCartEmpty = remember {
        mutableStateOf(true)
    }

    Scaffold(
        topBar = {
            CustomTopBar(
                title = stringResource(R.string.menu_my_cart),
                navController = navController,
                centeredTitle = true,
                actions = {
                    if (!isCartEmpty.value)
                        TextButton(onClick = {
                            showBottomSheet = true
                        }) {
                            Text(
                                text = stringResource(id = R.string.voucher_code),
                                color = MaterialTheme.colorScheme.primary,
                                fontWeight = FontWeight.Medium
                            )
                        }
                }
            )
        },

        bottomBar = {
            if (!isCartEmpty.value)
                CartSummaryBar(
                    subtotal = 45.75,
                    shippingCost = 0.00,
                    onCheckout = { /* Implement checkout logic */ },
                    cartItems = cartItems
                )
        }
    ) { paddingValues ->
        if (showBottomSheet) {

            ModalBottomSheet(
                containerColor = MaterialTheme.colorScheme.background,
                onDismissRequest = {
                    showBottomSheet = false
                },
                sheetState = sheetState
            ) {
                VoucherCodeBottomSheet(
                    onApply = { value ->
                        selectedVoucher = value
                        showBottomSheet = false
                    },

                    )

            }
        }
        if (isCartEmpty.value) {
            EmptyState(
                title = stringResource(id = R.string.empty_cart),
                description = stringResource(id = R.string.empty_cart_desc),
                buttonText = stringResource(id = R.string.explore_products),
                onButtonClick = { }
            )
        } else {
            LazyColumn(
                modifier = Modifier.padding(paddingValues),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(cartItems) { item ->
                    CartItemCard(
                        imageUrl = item.imageUrl,
                        productName = item.name,
                        currentPrice = item.currentPrice,
                        originalPrice = item.originalPrice,
                        quantity = item.quantity,
                        isChecked = item.isChecked,
                        onCheckedChange = { checked ->
                            cartItems = cartItems.map {
                                if (it.id == item.id) it.copy(isChecked = checked) else it
                            }
                        },
                        onQuantityChange = { newQuantity ->
                            cartItems = cartItems.map {
                                if (it.id == item.id) it.copy(quantity = newQuantity) else it
                            }
                        },
                        onDelete = {
                            cartItems = cartItems.filter { it.id != item.id }
                        }
                    )
                }
            }
        }

    }
}

data class CartItem(
    val id: Int,
    val name: String,
    val imageUrl: String,
    val currentPrice: Double,
    val originalPrice: Double,
    val quantity: Int,
    val isChecked: Boolean
)
