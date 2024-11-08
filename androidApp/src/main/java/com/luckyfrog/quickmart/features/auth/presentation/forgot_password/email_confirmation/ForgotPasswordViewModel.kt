package com.luckyfrog.quickmart.features.auth.presentation.forgot_password.email_confirmation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.luckyfrog.quickmart.core.generic.entities.MetaEntity
import com.luckyfrog.quickmart.features.auth.data.models.response.ForgotPasswordSendOTPFormRequestDto
import com.luckyfrog.quickmart.features.auth.domain.usecases.ForgotPasswordSendOTPUseCase
import com.luckyfrog.quickmart.utils.helper.ApiResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class ForgotPasswordState {
    data object Idle : ForgotPasswordState()
    data object Loading : ForgotPasswordState()
    data class Success(val data: MetaEntity) : ForgotPasswordState()
    data class Error(val message: String) : ForgotPasswordState()
}

@HiltViewModel
class ForgotPasswordViewModel @Inject constructor(
    private val _usecase: ForgotPasswordSendOTPUseCase,
) : ViewModel() {

    private val _state =
        MutableStateFlow<ForgotPasswordState>(ForgotPasswordState.Idle)
    val state: StateFlow<ForgotPasswordState> = _state

    fun sendOTP(params: ForgotPasswordSendOTPFormRequestDto) {
        viewModelScope.launch {
            _usecase.execute(params).collect { response ->
                when (response) {
                    is ApiResponse.Loading -> {
                        _state.value = ForgotPasswordState.Loading
                    }

                    is ApiResponse.Success -> {
                        _state.value = ForgotPasswordState.Success(response.data)
                    }

                    is ApiResponse.Failure -> {
                        _state.value =
                            ForgotPasswordState.Error(response.errorMessage)
                    }
                }
            }
        }
    }


}
