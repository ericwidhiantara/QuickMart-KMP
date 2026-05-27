package com.luckyfrog.quickmart.features.wallet.presentation

import com.luckyfrog.quickmart.features.wallet.domain.entities.TopUpParamsEntity
import com.luckyfrog.quickmart.features.wallet.domain.entities.WalletEntity
import com.luckyfrog.quickmart.features.wallet.domain.usecases.GetWalletUseCase
import com.luckyfrog.quickmart.features.wallet.domain.usecases.TopUpWalletUseCase
import com.luckyfrog.quickmart.utils.ApiResponse
import dev.icerock.moko.mvvm.flow.cStateFlow
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class WalletState {
    data object Idle : WalletState()
    data object Loading : WalletState()
    data class Success(val wallet: WalletEntity) : WalletState()
    data class Error(val message: String) : WalletState()
}

sealed class TopUpState {
    data object Idle : TopUpState()
    data object Loading : TopUpState()
    data class Success(val wallet: WalletEntity) : TopUpState()
    data class Error(val message: String) : TopUpState()
}

class WalletViewModel(
    private val getWalletUseCase: GetWalletUseCase,
    private val topUpWalletUseCase: TopUpWalletUseCase
) : ViewModel() {

    private val _walletState = MutableStateFlow<WalletState>(WalletState.Idle)
    val walletState = _walletState.asStateFlow().cStateFlow()

    private val _topUpState = MutableStateFlow<TopUpState>(TopUpState.Idle)
    val topUpState = _topUpState.asStateFlow().cStateFlow()

    fun fetchWallet() {
        viewModelScope.launch {
            getWalletUseCase.execute(Unit).collect { response ->
                _walletState.value = when (response) {
                    is ApiResponse.Loading -> WalletState.Loading
                    is ApiResponse.Success -> WalletState.Success(response.data.data!!)
                    is ApiResponse.Failure -> WalletState.Error(response.errorMessage)
                }
            }
        }
    }

    fun topUp(amount: Double) {
        viewModelScope.launch {
            topUpWalletUseCase.execute(TopUpParamsEntity(amount)).collect { response ->
                _topUpState.value = when (response) {
                    is ApiResponse.Loading -> TopUpState.Loading
                    is ApiResponse.Success -> TopUpState.Success(response.data.data!!)
                    is ApiResponse.Failure -> TopUpState.Error(response.errorMessage)
                }
            }
        }
    }

    fun resetTopUpState() { _topUpState.value = TopUpState.Idle }
}
