package com.luckyfrog.quickmart.features.product.presentation.product_list

import com.luckyfrog.quickmart.features.product.domain.entities.ProductEntity
import com.luckyfrog.quickmart.features.product.domain.entities.ProductFormParamsEntity
import com.luckyfrog.quickmart.features.product.domain.usecases.GetProductsUseCase
import com.luckyfrog.quickmart.utils.ApiResponse
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock

sealed class ProductState {
    data object Idle : ProductState()
    data class Success(
        val data: List<ProductEntity>,
        val isLastPage: Boolean,
        val isLoadingMore: Boolean = false  // New field to track pagination loading
    ) : ProductState()

    data object LoadingFirstPage : ProductState()
    data class Error(val message: String) : ProductState()
}

class ProductListViewModel(
    private val _usecase: GetProductsUseCase
) : ViewModel() {
    private val _state = MutableStateFlow<ProductState>(ProductState.Idle)
    val state: StateFlow<ProductState> = _state

    private var currentPage = 1
    private val productList = mutableListOf<ProductEntity>()
    private var isLastPage = false
    private var isLoadingMore = false  // Track if we're loading more items
    private var itemKeys = mutableMapOf<Int, Long>()  // Store index to timestamp mapping

    fun fetchProducts(params: ProductFormParamsEntity, isFirstLoad: Boolean = false) {
        if (isFirstLoad) {
            currentPage = 1
            productList.clear()
            _state.value = ProductState.LoadingFirstPage
        } else {
            if (isLastPage || isLoadingMore) return
            isLoadingMore = true
            // Update current success state to show loading more
            (_state.value as? ProductState.Success)?.let { currentState ->
                _state.value = currentState.copy(isLoadingMore = true)
            }
        }

        viewModelScope.launch {
            val paginatedParams = params.copy(page = currentPage)
            _usecase.execute(paginatedParams).collect { response ->
                when (response) {
                    is ApiResponse.Success -> {
                        val newData = response.data.data?.data ?: emptyList()
                        productList.addAll(newData)

                        isLastPage = newData.isEmpty() ||
                                (response.data.data?.data?.size ?: 0) < params.limit

                        _state.value = ProductState.Success(
                            data = productList.toList(),
                            isLastPage = isLastPage,
                            isLoadingMore = false
                        )

                        if (!isLastPage) {
                            currentPage++
                        }
                        isLoadingMore = false
                    }

                    is ApiResponse.Failure -> {
                        if (isFirstLoad) {
                            _state.value = ProductState.Error(response.errorMessage)
                        } else {
                            // If pagination fails, revert to previous success state without loading
                            (_state.value as? ProductState.Success)?.let { currentState ->
                                _state.value = currentState.copy(isLoadingMore = false)
                            }
                        }
                        isLoadingMore = false
                    }

                    else -> {} // Handle other cases if needed
                }
            }
        }
    }

    fun getKeyForIndex(index: Int): Long {
        return itemKeys[index] ?: run {
            val newKey = Clock.System.now().toEpochMilliseconds() + index
            itemKeys[index] = newKey
            newKey
        }
    }
}