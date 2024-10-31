package com.luckyfrog.quickmart.features.product.presentation.product_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.luckyfrog.quickmart.features.product.domain.entities.ProductEntity
import com.luckyfrog.quickmart.features.product.domain.usecases.GetProductDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    private val getProductDetailUseCase: GetProductDetailUseCase
) : ViewModel() {

    private val _productDetail = MutableStateFlow<ProductEntity?>(null)
    val productDetail: StateFlow<ProductEntity?> = _productDetail

    fun fetchProductDetail(productId: Int) {
        viewModelScope.launch {
            try {
                val product = getProductDetailUseCase.execute(productId)
                _productDetail.value = product
            } catch (e: Exception) {
                // Handle the error, e.g., show a message or log the error
                _productDetail.value = null
            }
        }
    }
}
