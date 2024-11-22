package com.luckyfrog.quickmart.features.cart.presentation.my_cart.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.luckyfrog.quickmart.R
import com.luckyfrog.quickmart.core.widgets.CustomOutlinedButton
import com.luckyfrog.quickmart.features.cart.presentation.my_cart.CartViewModel
import com.luckyfrog.quickmart.features.profile.presentation.profile.UserState
import com.luckyfrog.quickmart.features.profile.presentation.profile.UserViewModel
import java.util.Locale


@Composable
fun CartSummaryBar(
    shippingCost: Double,
    onCheckout: () -> Unit,
    viewModel: CartViewModel = hiltViewModel(),
    userViewModel: UserViewModel = hiltViewModel(),

    ) {

    LaunchedEffect(Unit) {
        userViewModel.getUserLogin()
        // when success get user login data, fetch cart items
        userViewModel.userState.collect { user ->
            if (user is UserState.Success) {
                viewModel.fetchSelectedItems(user.data.id)
                viewModel.calculateSubtotal(user.data.id)
            }
        }

    }
    val selectedItems = viewModel.selectedItems.collectAsState().value
    val subTotal = viewModel.subtotal.collectAsState().value

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = stringResource(id = R.string.order_info),
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
        )
        Spacer(modifier = Modifier.height(12.dp))

        OrderInfoRow(label = stringResource(id = R.string.subtotal), amount = subTotal)
        OrderInfoRow(label = stringResource(id = R.string.shipping_cost), amount = shippingCost)

        TotalOrderInfoRow(
            label = stringResource(id = R.string.total),
            amount = subTotal + shippingCost
        )

        Spacer(modifier = Modifier.height(24.dp))

        CustomOutlinedButton(
            buttonText = stringResource(R.string.checkout, selectedItems.size),
            onClick = onCheckout
        )
    }
}

@Composable
fun OrderInfoRow(label: String, amount: Double) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            fontSize = 12.sp,
            fontWeight = FontWeight.Normal,
        )
        Text(
            text = String.format(
                Locale.US,
                "$%.2f", amount
            ),
            fontSize = 12.sp,
            fontWeight = FontWeight.Normal,
        )
    }
}

@Composable
fun TotalOrderInfoRow(label: String, amount: Double) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
        )
        Text(
            text = String.format(
                Locale.US,
                "$%.2f", amount
            ),
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
        )
    }
}