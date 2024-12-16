package com.luckyfrog.quickmart.features.wishlist.presentation.my_wishlist

import com.luckyfrog.quickmart.features.wishlist.data.models.WishlistLocalItemDto
import com.luckyfrog.quickmart.features.wishlist.domain.usecases.local.DeleteWishlistItemUseCase
import com.luckyfrog.quickmart.features.wishlist.domain.usecases.local.GetWishlistItemsUseCase
import com.luckyfrog.quickmart.features.wishlist.domain.usecases.local.InsertWishlistItemUseCase
import dev.icerock.moko.mvvm.flow.cStateFlow
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MyWishlistViewModel(
    private val insertWishlistItemUseCase: InsertWishlistItemUseCase,
    private val deleteWishlistItemUseCase: DeleteWishlistItemUseCase,
    private val getWishlistItemsUseCase: GetWishlistItemsUseCase,
) : ViewModel() {

    private val _wishlistItems = MutableStateFlow<List<WishlistLocalItemDto>>(emptyList())
    val wishlistItems = _wishlistItems.asStateFlow().cStateFlow()

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
            fetchWishlistItems(wishlistItem.userId)
        }
    }

    fun deleteItem(wishlistItem: WishlistLocalItemDto) {
        viewModelScope.launch {
            deleteWishlistItemUseCase(wishlistItem)
            fetchWishlistItems(wishlistItem.userId)
        }
    }

    fun fetchWishlistItems(userId: String) {
        viewModelScope.launch {
            getWishlistItemsUseCase(userId).collect { items ->
                _wishlistItems.value = items
            }
        }
    }


}
