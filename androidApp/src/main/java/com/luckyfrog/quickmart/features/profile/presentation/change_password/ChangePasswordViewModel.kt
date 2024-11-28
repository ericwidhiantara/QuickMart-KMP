package com.luckyfrog.quickmart.features.profile.presentation.change_password

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.luckyfrog.quickmart.core.generic.entities.MetaEntity
import com.luckyfrog.quickmart.features.auth.data.models.request.ForgotPasswordChangePasswordFormRequestDto
import com.luckyfrog.quickmart.features.auth.domain.usecases.ForgotPasswordChangePasswordUseCase
import com.luckyfrog.quickmart.utils.helper.ApiResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class ChangePasswordState {
    data object Idle : ChangePasswordState()
    data object Loading : ChangePasswordState()
    data class Success(val data: MetaEntity) : ChangePasswordState()
    data class Error(val message: String) : ChangePasswordState()
}

@HiltViewModel
class ChangePasswordViewModel @Inject constructor(
    private val _usecase: ForgotPasswordChangePasswordUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<ChangePasswordState>(ChangePasswordState.Idle)
    val state: StateFlow<ChangePasswordState> = _state

    fun changePassword(params: ForgotPasswordChangePasswordFormRequestDto) {
        viewModelScope.launch {
            _usecase.execute(params).collect { response ->
                when (response) {
                    is ApiResponse.Loading -> {
                        _state.value = ChangePasswordState.Loading
                    }

                    is ApiResponse.Success -> {
                        _state.value = ChangePasswordState.Success(response.data)
                    }

                    is ApiResponse.Failure -> {
                        _state.value = ChangePasswordState.Error(response.errorMessage)
                    }
                }
            }
        }
    }

}
