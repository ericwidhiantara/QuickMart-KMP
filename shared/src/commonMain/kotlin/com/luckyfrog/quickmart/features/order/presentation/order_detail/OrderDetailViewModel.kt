package com.luckyfrog.quickmart.features.order.presentation.order_detail

import com.luckyfrog.quickmart.features.order.domain.entities.OrderEntity
import com.luckyfrog.quickmart.features.order.domain.usecases.CancelOrderUseCase
import com.luckyfrog.quickmart.features.order.domain.usecases.GetOrderDetailUseCase
import com.luckyfrog.quickmart.utils.ApiResponse
import dev.icerock.moko.mvvm.flow.cStateFlow
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class OrderDetailState {
    data object Idle : OrderDetailState()
    data object Loading : OrderDetailState()
    data class Success(val order: OrderEntity) : OrderDetailState()
    data class Error(val message: String) : OrderDetailState()
}

sealed class CancelOrderState {
    data object Idle : CancelOrderState()
    data object Loading : CancelOrderState()
    data class Success(val order: OrderEntity) : CancelOrderState()
    data class Error(val message: String) : CancelOrderState()
}

class OrderDetailViewModel(
    private val getOrderDetailUseCase: GetOrderDetailUseCase,
    private val cancelOrderUseCase: CancelOrderUseCase
) : ViewModel() {

    private val _detailState = MutableStateFlow<OrderDetailState>(OrderDetailState.Idle)
    val detailState = _detailState.asStateFlow().cStateFlow()

    private val _cancelState = MutableStateFlow<CancelOrderState>(CancelOrderState.Idle)
    val cancelState = _cancelState.asStateFlow().cStateFlow()

    fun fetchOrderDetail(id: String) {
        _detailState.value = OrderDetailState.Loading
        viewModelScope.launch {
            getOrderDetailUseCase.execute(id).collect { response ->
                when (response) {
                    is ApiResponse.Success -> {
                        val order = response.data.data
                        if (order != null) {
                            _detailState.value = OrderDetailState.Success(order = order)
                        } else {
                            _detailState.value = OrderDetailState.Error("Order not found")
                        }
                    }
                    is ApiResponse.Failure -> {
                        _detailState.value = OrderDetailState.Error(response.errorMessage)
                    }
                    else -> {}
                }
            }
        }
    }

    fun cancelOrder(id: String) {
        _cancelState.value = CancelOrderState.Loading
        viewModelScope.launch {
            cancelOrderUseCase.execute(id).collect { response ->
                when (response) {
                    is ApiResponse.Success -> {
                        val order = response.data.data
                        if (order != null) {
                            _cancelState.value = CancelOrderState.Success(order = order)
                            _detailState.value = OrderDetailState.Success(order = order)
                        } else {
                            _cancelState.value = CancelOrderState.Error("Cancel failed")
                        }
                    }
                    is ApiResponse.Failure -> {
                        _cancelState.value = CancelOrderState.Error(response.errorMessage)
                    }
                    else -> {}
                }
            }
        }
    }

    fun resetCancelState() {
        _cancelState.value = CancelOrderState.Idle
    }
}
