package com.luckyfrog.quickmart.features.order.presentation.order_list

import com.luckyfrog.quickmart.features.order.domain.entities.CheckoutParamsEntity
import com.luckyfrog.quickmart.features.order.domain.entities.GetOrdersParamsEntity
import com.luckyfrog.quickmart.features.order.domain.entities.OrderEntity
import com.luckyfrog.quickmart.features.order.domain.usecases.CheckoutUseCase
import com.luckyfrog.quickmart.features.order.domain.usecases.GetOrdersUseCase
import com.luckyfrog.quickmart.utils.ApiResponse
import dev.icerock.moko.mvvm.flow.cStateFlow
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class OrderListState {
    data object Idle : OrderListState()
    data object Loading : OrderListState()
    data class Success(
        val data: List<OrderEntity>,
        val isLastPage: Boolean,
        val isLoadingMore: Boolean = false
    ) : OrderListState()
    data class Error(val message: String) : OrderListState()
}

sealed class CheckoutState {
    data object Idle : CheckoutState()
    data object Loading : CheckoutState()
    data class Success(val order: OrderEntity) : CheckoutState()
    data class Error(val message: String) : CheckoutState()
}

class OrderListViewModel(
    private val getOrdersUseCase: GetOrdersUseCase,
    private val checkoutUseCase: CheckoutUseCase
) : ViewModel() {

    private val _orderListState = MutableStateFlow<OrderListState>(OrderListState.Idle)
    val orderListState = _orderListState.asStateFlow().cStateFlow()

    private val _checkoutState = MutableStateFlow<CheckoutState>(CheckoutState.Idle)
    val checkoutState = _checkoutState.asStateFlow().cStateFlow()

    private var currentPage = 1
    private val orderList = mutableListOf<OrderEntity>()
    private var isLastPage = false
    private var isLoadingMore = false
    private var currentStatus: String? = null

    fun fetchOrders(status: String? = null, isFirstLoad: Boolean = true) {
        if (isFirstLoad) {
            currentPage = 1
            currentStatus = status
            orderList.clear()
            _orderListState.value = OrderListState.Loading
        } else {
            if (isLastPage || isLoadingMore) return
            isLoadingMore = true
            (_orderListState.value as? OrderListState.Success)?.let {
                _orderListState.value = it.copy(isLoadingMore = true)
            }
        }

        viewModelScope.launch {
            val params = GetOrdersParamsEntity(status = currentStatus, page = currentPage)
            getOrdersUseCase.execute(params).collect { response ->
                when (response) {
                    is ApiResponse.Success -> {
                        val newData = response.data.data?.data ?: emptyList()
                        orderList.addAll(newData)
                        isLastPage = newData.isEmpty() || (response.data.data?.data?.size ?: 0) < 10
                        _orderListState.value = OrderListState.Success(
                            data = orderList.toList(),
                            isLastPage = isLastPage,
                            isLoadingMore = false
                        )
                        if (!isLastPage) currentPage++
                        isLoadingMore = false
                    }
                    is ApiResponse.Failure -> {
                        if (isFirstLoad) {
                            _orderListState.value = OrderListState.Error(response.errorMessage)
                        } else {
                            (_orderListState.value as? OrderListState.Success)?.let {
                                _orderListState.value = it.copy(isLoadingMore = false)
                            }
                        }
                        isLoadingMore = false
                    }
                    else -> {}
                }
            }
        }
    }

    fun checkout(cartItemIds: List<String>? = null) {
        _checkoutState.value = CheckoutState.Loading
        viewModelScope.launch {
            checkoutUseCase.execute(CheckoutParamsEntity(cartItemIds = cartItemIds)).collect { response ->
                when (response) {
                    is ApiResponse.Success -> {
                        val order = response.data.data
                        if (order != null) {
                            _checkoutState.value = CheckoutState.Success(order = order)
                        } else {
                            _checkoutState.value = CheckoutState.Error("Checkout failed")
                        }
                    }
                    is ApiResponse.Failure -> {
                        _checkoutState.value = CheckoutState.Error(response.errorMessage)
                    }
                    else -> {}
                }
            }
        }
    }

    fun resetCheckoutState() {
        _checkoutState.value = CheckoutState.Idle
    }
}
