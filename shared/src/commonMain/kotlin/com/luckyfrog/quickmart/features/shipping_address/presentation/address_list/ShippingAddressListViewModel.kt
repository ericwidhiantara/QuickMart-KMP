package com.luckyfrog.quickmart.features.shipping_address.presentation.address_list

import com.luckyfrog.quickmart.features.shipping_address.domain.entities.CreateShippingAddressParamsEntity
import com.luckyfrog.quickmart.features.shipping_address.domain.entities.ShippingAddressEntity
import com.luckyfrog.quickmart.features.shipping_address.domain.entities.UpdateShippingAddressParamsEntity
import com.luckyfrog.quickmart.features.shipping_address.domain.usecases.CreateShippingAddressUseCase
import com.luckyfrog.quickmart.features.shipping_address.domain.usecases.DeleteShippingAddressUseCase
import com.luckyfrog.quickmart.features.shipping_address.domain.usecases.GetShippingAddressesUseCase
import com.luckyfrog.quickmart.features.shipping_address.domain.usecases.SetDefaultShippingAddressUseCase
import com.luckyfrog.quickmart.features.shipping_address.domain.usecases.UpdateShippingAddressUseCase
import com.luckyfrog.quickmart.utils.ApiResponse
import dev.icerock.moko.mvvm.flow.cStateFlow
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class AddressListState {
    data object Idle : AddressListState()
    data object Loading : AddressListState()
    data class Success(val addresses: List<ShippingAddressEntity>) : AddressListState()
    data class Error(val message: String) : AddressListState()
}

sealed class AddressMutationState {
    data object Idle : AddressMutationState()
    data object Loading : AddressMutationState()
    data class Success(val address: ShippingAddressEntity?) : AddressMutationState()
    data object Deleted : AddressMutationState()
    data class Error(val message: String) : AddressMutationState()
}

class ShippingAddressListViewModel(
    private val getShippingAddressesUseCase: GetShippingAddressesUseCase,
    private val createShippingAddressUseCase: CreateShippingAddressUseCase,
    private val updateShippingAddressUseCase: UpdateShippingAddressUseCase,
    private val deleteShippingAddressUseCase: DeleteShippingAddressUseCase,
    private val setDefaultShippingAddressUseCase: SetDefaultShippingAddressUseCase,
) : ViewModel() {

    private val _listState = MutableStateFlow<AddressListState>(AddressListState.Idle)
    val listState = _listState.asStateFlow().cStateFlow()

    private val _mutationState = MutableStateFlow<AddressMutationState>(AddressMutationState.Idle)
    val mutationState = _mutationState.asStateFlow().cStateFlow()

    fun fetchAddresses() {
        _listState.value = AddressListState.Loading
        viewModelScope.launch {
            getShippingAddressesUseCase.execute(Unit).collect { response ->
                when (response) {
                    is ApiResponse.Success -> {
                        _listState.value = AddressListState.Success(
                            addresses = response.data.data ?: emptyList()
                        )
                    }
                    is ApiResponse.Failure -> {
                        _listState.value = AddressListState.Error(response.errorMessage)
                    }
                    else -> {}
                }
            }
        }
    }

    fun createAddress(params: CreateShippingAddressParamsEntity) {
        _mutationState.value = AddressMutationState.Loading
        viewModelScope.launch {
            createShippingAddressUseCase.execute(params).collect { response ->
                when (response) {
                    is ApiResponse.Success -> {
                        _mutationState.value = AddressMutationState.Success(response.data.data)
                        fetchAddresses()
                    }
                    is ApiResponse.Failure -> {
                        _mutationState.value = AddressMutationState.Error(response.errorMessage)
                    }
                    else -> {}
                }
            }
        }
    }

    fun updateAddress(params: UpdateShippingAddressParamsEntity) {
        _mutationState.value = AddressMutationState.Loading
        viewModelScope.launch {
            updateShippingAddressUseCase.execute(params).collect { response ->
                when (response) {
                    is ApiResponse.Success -> {
                        _mutationState.value = AddressMutationState.Success(response.data.data)
                        fetchAddresses()
                    }
                    is ApiResponse.Failure -> {
                        _mutationState.value = AddressMutationState.Error(response.errorMessage)
                    }
                    else -> {}
                }
            }
        }
    }

    fun deleteAddress(id: String) {
        _mutationState.value = AddressMutationState.Loading
        viewModelScope.launch {
            deleteShippingAddressUseCase.execute(id).collect { response ->
                when (response) {
                    is ApiResponse.Success -> {
                        _mutationState.value = AddressMutationState.Deleted
                        fetchAddresses()
                    }
                    is ApiResponse.Failure -> {
                        _mutationState.value = AddressMutationState.Error(response.errorMessage)
                    }
                    else -> {}
                }
            }
        }
    }

    fun setDefault(id: String) {
        _mutationState.value = AddressMutationState.Loading
        viewModelScope.launch {
            setDefaultShippingAddressUseCase.execute(id).collect { response ->
                when (response) {
                    is ApiResponse.Success -> {
                        _mutationState.value = AddressMutationState.Success(response.data.data)
                        fetchAddresses()
                    }
                    is ApiResponse.Failure -> {
                        _mutationState.value = AddressMutationState.Error(response.errorMessage)
                    }
                    else -> {}
                }
            }
        }
    }

    fun resetMutationState() {
        _mutationState.value = AddressMutationState.Idle
    }
}
