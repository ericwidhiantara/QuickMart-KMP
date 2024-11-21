package com.luckyfrog.quickmart.features.cart.presentation.my_cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.luckyfrog.quickmart.features.cart.data.model.CartLocalItemDto
import com.luckyfrog.quickmart.features.cart.domain.usecases.local.CalculateSubtotalUseCase
import com.luckyfrog.quickmart.features.cart.domain.usecases.local.DeleteCartItemUseCase
import com.luckyfrog.quickmart.features.cart.domain.usecases.local.GetCartItemsUseCase
import com.luckyfrog.quickmart.features.cart.domain.usecases.local.GetSelectedCartItemsUseCase
import com.luckyfrog.quickmart.features.cart.domain.usecases.local.InsertCartItemUseCase
import com.luckyfrog.quickmart.features.cart.domain.usecases.local.UpdateCartItemUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CartViewModel @Inject constructor(
    private val insertCartItemUseCase: InsertCartItemUseCase,
    private val updateCartItemUseCase: UpdateCartItemUseCase,
    private val deleteCartItemUseCase: DeleteCartItemUseCase,
    private val getCartItemsUseCase: GetCartItemsUseCase,
    private val getSelectedCartItemsUseCase: GetSelectedCartItemsUseCase,
    private val calculateSubtotalUseCase: CalculateSubtotalUseCase,
) : ViewModel() {

    private val _cartItems = MutableStateFlow<List<CartLocalItemDto>>(emptyList())
    val cartItems = _cartItems.asStateFlow()

    private val _selectedItems = MutableStateFlow<List<CartLocalItemDto>>(emptyList())
    val selectedItems = _selectedItems.asStateFlow()

    private val _subtotal = MutableStateFlow(0.0)
    val subtotal = _subtotal.asStateFlow()

    fun addItem(cartItem: CartLocalItemDto) {
        viewModelScope.launch {
            insertCartItemUseCase(cartItem)
            fetchCartItems()
            fetchSelectedItems()
            calculateSubtotal()
        }
    }

    fun updateItem(cartItem: CartLocalItemDto) {
        viewModelScope.launch {
            updateCartItemUseCase(cartItem)
            fetchCartItems()
            fetchSelectedItems()
            calculateSubtotal()
        }
    }

    fun deleteItem(cartItem: CartLocalItemDto) {
        viewModelScope.launch {
            deleteCartItemUseCase(cartItem)
            fetchCartItems()
            fetchSelectedItems()
            calculateSubtotal()
        }
    }

    fun fetchCartItems() {
        viewModelScope.launch {
            getCartItemsUseCase().collect { items ->
                _cartItems.value = items
            }
        }
    }

    fun fetchSelectedItems() {
        viewModelScope.launch {
            getSelectedCartItemsUseCase().collect { items ->
                _selectedItems.value = items
            }
        }
    }

    fun calculateSubtotal() {
        viewModelScope.launch {
            _subtotal.value = calculateSubtotalUseCase().toList().firstOrNull() ?: 0.0
        }
    }

}
