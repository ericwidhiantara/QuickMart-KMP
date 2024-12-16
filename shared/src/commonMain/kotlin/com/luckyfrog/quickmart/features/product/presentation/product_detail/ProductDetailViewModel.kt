package com.luckyfrog.quickmart.features.product.presentation.product_detail

import com.luckyfrog.quickmart.features.product.domain.entities.ProductEntity
import com.luckyfrog.quickmart.features.product.domain.usecases.GetProductDetailUseCase
import com.luckyfrog.quickmart.utils.ApiResponse
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

// ProductDetailState.kt
sealed class ProductDetailState {
    data object Idle : ProductDetailState()
    data object Loading : ProductDetailState()
    data class Success(val data: ProductEntity) : ProductDetailState()
    data class Error(val message: String) : ProductDetailState()
}

class ProductDetailViewModel(
    private val _useCase: GetProductDetailUseCase
) : ViewModel() {
    private val _state = MutableStateFlow<ProductDetailState>(ProductDetailState.Idle)
    val state: StateFlow<ProductDetailState> = _state

    fun fetchProductDetail(productId: String) {
        _state.value = ProductDetailState.Loading

        viewModelScope.launch {
            _useCase.execute(productId).collect { response ->
                when (response) {
                    is ApiResponse.Success -> {
                        response.data.let { product ->
                            _state.value = ProductDetailState.Success(product.data!!)
                        }
                    }

                    is ApiResponse.Failure -> {
                        _state.value = ProductDetailState.Error(response.errorMessage)
                    }

                    else -> {} // Handle other cases if needed
                }
            }
        }
    }
}