package com.luckyfrog.quickmart.features.product.presentation.product_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.luckyfrog.quickmart.core.generic.entities.PaginationEntity
import com.luckyfrog.quickmart.features.product.domain.entities.ProductEntity
import com.luckyfrog.quickmart.features.product.domain.entities.ProductFormParamsEntity
import com.luckyfrog.quickmart.features.product.domain.usecases.GetProductsUseCase
import com.luckyfrog.quickmart.utils.helper.ApiResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


sealed class ProductState {
    data object Idle : ProductState()
    data object Loading : ProductState()
    data class Success(val data: PaginationEntity<ProductEntity>) : ProductState()
    data class Error(val message: String) : ProductState()
}

@HiltViewModel
class ProductListViewModel @Inject constructor(
    private val _usecase: GetProductsUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<ProductState>(ProductState.Idle)
    val state: StateFlow<ProductState> = _state

    fun fetchProducts(params: ProductFormParamsEntity) {
        viewModelScope.launch {
            _usecase.execute(params).collect { response ->
                when (response) {
                    is ApiResponse.Loading -> {
                        _state.value = ProductState.Loading
                    }

                    is ApiResponse.Success -> {
                        _state.value = ProductState.Success(response.data.data!!)
                    }

                    is ApiResponse.Failure -> {
                        _state.value = ProductState.Error(response.errorMessage)
                    }
                }
            }
        }
    }
}