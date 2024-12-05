package com.luckyfrog.quickmart.features.auth.presentation.email_verification

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.luckyfrog.quickmart.core.generic.entities.MetaEntity
import com.luckyfrog.quickmart.core.generic.mapper.toEntity
import com.luckyfrog.quickmart.features.auth.data.models.request.VerifyOTPFormRequestDto
import com.luckyfrog.quickmart.features.auth.domain.usecases.SendOTPUseCase
import com.luckyfrog.quickmart.features.auth.domain.usecases.VerifyOTPUseCase
import com.luckyfrog.quickmart.utils.ApiResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class EmailVerificationState {
    data object Idle : EmailVerificationState()
    data object Loading : EmailVerificationState()
    data class Success(val data: MetaEntity) : EmailVerificationState()
    data class Error(val message: String) : EmailVerificationState()
}

class EmailVerificationViewModel(
    private val _usecase: SendOTPUseCase,
    private val _verifyUsecase: VerifyOTPUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<EmailVerificationState>(EmailVerificationState.Idle)
    val state: StateFlow<EmailVerificationState> = _state

    fun sendOTP() {
        viewModelScope.launch {
            _usecase.execute(Unit).collect { response ->
                when (response) {
                    is ApiResponse.Loading -> {
                        _state.value = EmailVerificationState.Loading
                    }

                    is ApiResponse.Success -> {
                        _state.value =
                            EmailVerificationState.Success(response.data.meta?.toEntity()!!)
                    }

                    is ApiResponse.Failure -> {
                        _state.value = EmailVerificationState.Error(response.errorMessage)
                    }
                }
            }
        }
    }

    fun verifyOTP(params: VerifyOTPFormRequestDto) {
        viewModelScope.launch {
            _verifyUsecase.execute(params).collect { response ->
                when (response) {
                    is ApiResponse.Loading -> {
                        _state.value = EmailVerificationState.Loading
                    }

                    is ApiResponse.Success -> {
                        _state.value =
                            EmailVerificationState.Success(response.data.meta?.toEntity()!!)
                    }

                    is ApiResponse.Failure -> {
                        _state.value = EmailVerificationState.Error(response.errorMessage)
                    }
                }
            }
        }
    }

}
