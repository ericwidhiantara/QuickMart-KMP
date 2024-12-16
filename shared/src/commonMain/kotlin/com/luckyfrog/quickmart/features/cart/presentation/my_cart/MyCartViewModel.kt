package com.luckyfrog.quickmart.features.cart.presentation.my_cart

import com.luckyfrog.quickmart.features.cart.data.model.CartLocalItemDto
import com.luckyfrog.quickmart.features.cart.domain.usecases.local.CalculateSubtotalUseCase
import com.luckyfrog.quickmart.features.cart.domain.usecases.local.DeleteCartItemUseCase
import com.luckyfrog.quickmart.features.cart.domain.usecases.local.GetCartItemsUseCase
import com.luckyfrog.quickmart.features.cart.domain.usecases.local.GetSelectedCartItemsUseCase
import com.luckyfrog.quickmart.features.cart.domain.usecases.local.InsertCartItemUseCase
import com.luckyfrog.quickmart.features.cart.domain.usecases.local.UpdateCartItemUseCase
import dev.icerock.moko.mvvm.flow.cStateFlow
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch


class MyCartViewModel(
    private val insertCartItemUseCase: InsertCartItemUseCase,
    private val updateCartItemUseCase: UpdateCartItemUseCase,
    private val deleteCartItemUseCase: DeleteCartItemUseCase,
    private val getCartItemsUseCase: GetCartItemsUseCase,
    private val getSelectedCartItemsUseCase: GetSelectedCartItemsUseCase,
    private val calculateSubtotalUseCase: CalculateSubtotalUseCase,
) : ViewModel() {

    private val _cartItems = MutableStateFlow<List<CartLocalItemDto>>(emptyList())
    val cartItems = _cartItems.asStateFlow().cStateFlow()

    private val _selectedItems = MutableStateFlow<List<CartLocalItemDto>>(emptyList())
    val selectedItems = _selectedItems.asStateFlow().cStateFlow()

    private val _subtotal = MutableStateFlow(0.0)
    val subtotal = _subtotal.asStateFlow().cStateFlow()

    fun addItem(cartItem: CartLocalItemDto) {
        viewModelScope.launch {
            // check if item already exists
            val existingItem = cartItems.value.find { it.id == cartItem.id }

            if (existingItem != null) {
                // update the quantity of the existing item
                val updatedItem = existingItem.copy(qty = existingItem.qty + cartItem.qty)
                updateCartItemUseCase(updatedItem)
            } else {
                // insert the new item
                insertCartItemUseCase(cartItem)
            }
            fetchCartItems(cartItem.userId)
            fetchSelectedItems(cartItem.userId)
            calculateSubtotal(cartItem.userId)
        }
    }

    fun updateItem(cartItem: CartLocalItemDto) {
        viewModelScope.launch {
            updateCartItemUseCase(cartItem)
            fetchCartItems(cartItem.userId)
            fetchSelectedItems(cartItem.userId)
            calculateSubtotal(cartItem.userId)
        }
    }

    fun deleteItem(cartItem: CartLocalItemDto) {
        viewModelScope.launch {
            deleteCartItemUseCase(cartItem)
            fetchCartItems(cartItem.userId)
            fetchSelectedItems(cartItem.userId)
            calculateSubtotal(cartItem.userId)
        }
    }

    fun fetchCartItems(userId: String) {
        viewModelScope.launch {
            getCartItemsUseCase(userId).collect { items ->
                _cartItems.value = items
            }
        }
    }

    fun fetchSelectedItems(userId: String) {
        viewModelScope.launch {
            getSelectedCartItemsUseCase(userId).collect { items ->
                _selectedItems.value = items
            }
        }
    }

    fun calculateSubtotal(userId: String) {
        viewModelScope.launch {
            _subtotal.value = calculateSubtotalUseCase(userId).toList().firstOrNull() ?: 0.0
        }
    }

}
