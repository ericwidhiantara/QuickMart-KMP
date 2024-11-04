package com.luckyfrog.quickmart.features.product.presentation.product_list_by_category

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.luckyfrog.quickmart.features.product.domain.entities.ProductEntity
import com.luckyfrog.quickmart.features.product.domain.entities.ProductFormParamsEntity
import com.luckyfrog.quickmart.features.product.domain.usecases.GetProductsByCategoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductListByCategoryViewModel @Inject constructor(
    private val getProductsByCategoryUseCase: GetProductsByCategoryUseCase,
) : ViewModel() {

    private val _productsState: MutableStateFlow<PagingData<ProductEntity>> =
        MutableStateFlow(value = PagingData.empty())
    val productsState = _productsState.asStateFlow()

    // Store the current category slug
    private var productParams: ProductFormParamsEntity? = null

    fun onEvent(event: ProductListByCategory) {
        viewModelScope.launch {
            when (event) {
                is ProductListByCategory.GetProductsByCategory -> {
                    productParams = event.params
                    getProductsByCategory(event.params)
                }
            }
        }
    }

    private suspend fun getProductsByCategory(params: ProductFormParamsEntity) {
        getProductsByCategoryUseCase.execute(params)
            .distinctUntilChanged()
            .cachedIn(viewModelScope)
            .collectLatest { pagingData ->
                _productsState.value = pagingData
            }
    }
}

sealed class ProductListByCategory {
    data class GetProductsByCategory(val params: ProductFormParamsEntity) : ProductListByCategory()
}
