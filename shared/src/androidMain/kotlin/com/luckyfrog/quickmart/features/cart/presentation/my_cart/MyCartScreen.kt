package com.luckyfrog.quickmart.features.cart.presentation.my_cart

import android.widget.Toast
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.luckyfrog.quickmart.R
import com.luckyfrog.quickmart.core.widgets.ConfirmationDialog
import com.luckyfrog.quickmart.core.widgets.CustomTopBar
import com.luckyfrog.quickmart.core.widgets.EmptyState
import com.luckyfrog.quickmart.features.cart.presentation.my_cart.component.CartItemCard
import com.luckyfrog.quickmart.features.cart.presentation.my_cart.component.CartSummaryBar
import com.luckyfrog.quickmart.features.cart.presentation.my_cart.component.VoucherCodeBottomSheet
import com.luckyfrog.quickmart.features.general.presentation.main.NavBarViewModel
import com.luckyfrog.quickmart.features.profile.presentation.profile.UserState
import com.luckyfrog.quickmart.features.profile.presentation.profile.UserViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyCartScreen(
    navController: NavController,
    navBarViewModel: NavBarViewModel = koinViewModel<NavBarViewModel>(),
    viewModel: MyCartViewModel = koinViewModel<MyCartViewModel>(),
    userViewModel: UserViewModel = koinViewModel<UserViewModel>(),
) {
    LaunchedEffect(Unit) {
        userViewModel.getUserLogin()
        // when success get user login data, fetch cart items
        userViewModel.userState.collect { user ->
            if (user is UserState.Success) {
                viewModel.fetchCartItems(user.data.id)
                viewModel.fetchSelectedItems(user.data.id)
            }
        }

    }

    val items by viewModel.cartItems.collectAsStateWithLifecycle()
    val selectedItems by viewModel.selectedItems.collectAsStateWithLifecycle()

    var selectedVoucher by remember { mutableStateOf("") }
    var showBottomSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()
    val isCartEmpty = items.isEmpty();

    Scaffold(
        topBar = {
            CustomTopBar(
                title = stringResource(R.string.menu_my_cart),
                navController = navController,
                centeredTitle = isCartEmpty,
                actions = {
                    if (!isCartEmpty) {
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

                }
            )
        },
        bottomBar = {
            if (selectedItems.isNotEmpty())
                CartSummaryBar(
                    shippingCost = 0.00,
                    onCheckout = { /* Implement checkout logic */ },
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
        if (isCartEmpty) {
            EmptyState(
                title = stringResource(id = R.string.empty_cart),
                description = stringResource(id = R.string.empty_cart_desc),
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
                        ConfirmationDialog(
                            onConfirm = {
                                coroutineScope.launch {
                                    viewModel.deleteItem(item)
                                    Toast.makeText(
                                        context,
                                        context.getString(R.string.item_deleted_successfully),
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
                    CartItemCard(
                        imageUrl = item.productImage,
                        productName = item.productName,
                        currentPrice = item.productPrice - (item.productPrice * item.discountPercentage / 100),
                        originalPrice = item.productPrice,
                        quantity = item.qty,
                        isChecked = item.selected,
                        onCheckedChange = { checked ->
                            viewModel.updateItem(item.copy(selected = checked))
                        },
                        onQuantityChange = { newQuantity ->
                            viewModel.updateItem(item.copy(qty = newQuantity))
                        },
                        onDelete = {
                            showDeleteDialog = true
                        }
                    )
                }
            }
        }

    }
}

