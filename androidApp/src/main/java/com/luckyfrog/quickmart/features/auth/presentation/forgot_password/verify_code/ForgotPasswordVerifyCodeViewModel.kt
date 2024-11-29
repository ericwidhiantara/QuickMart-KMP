package com.luckyfrog.quickmart.features.auth.presentation.forgot_password.verify_code

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.luckyfrog.quickmart.features.auth.data.models.request.ForgotPasswordVerifyOTPFormRequestDto
import com.luckyfrog.quickmart.features.auth.domain.entities.ForgotPasswordVerifyCodeResponseEntity
import com.luckyfrog.quickmart.features.auth.domain.usecases.ForgotPasswordVerifyOTPUseCase
import com.luckyfrog.quickmart.utils.helper.ApiResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class ForgotPasswordVerifyCodeState {
    data object Idle : ForgotPasswordVerifyCodeState()
    data object Loading : ForgotPasswordVerifyCodeState()
    data class Success(val data: ForgotPasswordVerifyCodeResponseEntity) :
        ForgotPasswordVerifyCodeState()

    data class Error(val message: String) : ForgotPasswordVerifyCodeState()
}

@HiltViewModel
class ForgotPasswordVerifyCodeViewModel @Inject constructor(
    private val _usecase: ForgotPasswordVerifyOTPUseCase
) : ViewModel() {

    private val _state =
        MutableStateFlow<ForgotPasswordVerifyCodeState>(ForgotPasswordVerifyCodeState.Idle)
    val state: StateFlow<ForgotPasswordVerifyCodeState> = _state

    fun verifyOTP(params: ForgotPasswordVerifyOTPFormRequestDto) {
        viewModelScope.launch {
            _usecase.execute(params).collect { response ->
                when (response) {
                    is ApiResponse.Loading -> {
                        _state.value = ForgotPasswordVerifyCodeState.Loading
                    }

                    is ApiResponse.Success -> {
                        _state.value =
                            ForgotPasswordVerifyCodeState.Success(response.data.data!!)
                    }

                    is ApiResponse.Failure -> {
                        _state.value = ForgotPasswordVerifyCodeState.Error(response.errorMessage)
                    }
                }
            }
        }
    }

}
