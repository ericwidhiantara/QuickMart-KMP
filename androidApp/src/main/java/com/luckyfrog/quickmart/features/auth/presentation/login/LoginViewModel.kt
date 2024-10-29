package com.luckyfrog.quickmart.features.auth.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.luckyfrog.quickmart.features.auth.data.models.response.LoginFormRequestDto
import com.luckyfrog.quickmart.features.auth.domain.entities.LoginEntity
import com.luckyfrog.quickmart.features.auth.domain.usecases.LoginUseCase
import com.luckyfrog.quickmart.utils.helper.ApiResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class LoginState {
    data object Idle : LoginState()
    data object Loading : LoginState()
    data class Success(val data: LoginEntity) : LoginState()
    data class Error(val message: String) : LoginState()
}

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    private val _loginState = MutableStateFlow<LoginState>(LoginState.Idle)
    val loginState: StateFlow<LoginState> = _loginState

    fun login(params: LoginFormRequestDto) {
        viewModelScope.launch {
            loginUseCase.execute(params).collect { response ->
                when (response) {
                    is ApiResponse.Loading -> {
                        _loginState.value = LoginState.Loading
                    }

                    is ApiResponse.Success -> {
                        _loginState.value = LoginState.Success(response.data)
                    }

                    is ApiResponse.Failure -> {
                        _loginState.value = LoginState.Error(response.errorMessage)
                    }
                }
            }
        }
    }
}
