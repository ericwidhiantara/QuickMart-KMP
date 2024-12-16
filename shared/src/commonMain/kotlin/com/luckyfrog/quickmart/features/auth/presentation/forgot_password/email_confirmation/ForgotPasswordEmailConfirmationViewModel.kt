package com.luckyfrog.quickmart.features.auth.presentation.forgot_password.email_confirmation

import com.luckyfrog.quickmart.core.generic.entities.MetaEntity
import com.luckyfrog.quickmart.core.generic.mapper.toEntity
import com.luckyfrog.quickmart.features.auth.data.models.request.ForgotPasswordSendOTPFormRequestDto
import com.luckyfrog.quickmart.features.auth.domain.usecases.ForgotPasswordSendOTPUseCase
import com.luckyfrog.quickmart.utils.ApiResponse
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class ForgotPasswordEmailConfirmationState {
    data object Idle : ForgotPasswordEmailConfirmationState()
    data object Loading : ForgotPasswordEmailConfirmationState()
    data class Success(val data: MetaEntity) : ForgotPasswordEmailConfirmationState()
    data class Error(val message: String) : ForgotPasswordEmailConfirmationState()
}

class ForgotPasswordEmailConfirmationViewModel(
    private val _usecase: ForgotPasswordSendOTPUseCase,
) : ViewModel() {

    private val _state =
        MutableStateFlow<ForgotPasswordEmailConfirmationState>(ForgotPasswordEmailConfirmationState.Idle)
    val state: StateFlow<ForgotPasswordEmailConfirmationState> = _state

    fun sendOTP(params: ForgotPasswordSendOTPFormRequestDto) {
        viewModelScope.launch {
            _usecase.execute(params).collect { response ->
                when (response) {
                    is ApiResponse.Loading -> {
                        _state.value = ForgotPasswordEmailConfirmationState.Loading
                    }

                    is ApiResponse.Success -> {
                        _state.value =
                            ForgotPasswordEmailConfirmationState.Success(response.data.meta?.toEntity()!!)
                    }

                    is ApiResponse.Failure -> {
                        _state.value =
                            ForgotPasswordEmailConfirmationState.Error(response.errorMessage)
                    }
                }
            }
        }
    }


}
