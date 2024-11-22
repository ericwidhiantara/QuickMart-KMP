package com.luckyfrog.quickmart.features.wishlist.presentation.wishlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.luckyfrog.quickmart.features.wishlist.data.model.WishlistLocalItemDto
import com.luckyfrog.quickmart.features.wishlist.domain.usecases.local.DeleteWishlistItemUseCase
import com.luckyfrog.quickmart.features.wishlist.domain.usecases.local.GetWishlistItemsUseCase
import com.luckyfrog.quickmart.features.wishlist.domain.usecases.local.InsertWishlistItemUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class WishlistViewModel @Inject constructor(
    private val insertWishlistItemUseCase: InsertWishlistItemUseCase,
    private val deleteWishlistItemUseCase: DeleteWishlistItemUseCase,
    private val getWishlistItemsUseCase: GetWishlistItemsUseCase,
) : ViewModel() {

    private val _wishlistItems = MutableStateFlow<List<WishlistLocalItemDto>>(emptyList())
    val wishlistItems = _wishlistItems.asStateFlow()

    fun addItem(wishlistItem: WishlistLocalItemDto) {
        viewModelScope.launch {
            // check if item already exists
            val existingItem = wishlistItems.value.find { it.id == wishlistItem.id }

            if (existingItem != null) {
                // delete the existing item
                deleteWishlistItemUseCase(existingItem)
            } else {
                // insert the new item
                insertWishlistItemUseCase(wishlistItem)
            }
            fetchWishlistItems()
        }
    }

    fun deleteItem(wishlistItem: WishlistLocalItemDto) {
        viewModelScope.launch {
            deleteWishlistItemUseCase(wishlistItem)
            fetchWishlistItems()
        }
    }

    fun fetchWishlistItems() {
        viewModelScope.launch {
            getWishlistItemsUseCase().collect { items ->
                _wishlistItems.value = items
            }
        }
    }
    

}
