package com.luckyfrog.quickmart.features.auth.presentation.login

import com.luckyfrog.quickmart.features.auth.data.models.request.LoginFormRequestDto
import com.luckyfrog.quickmart.features.auth.domain.entities.AuthEntity
import com.luckyfrog.quickmart.features.auth.domain.usecases.LoginUseCase
import com.luckyfrog.quickmart.utils.ApiResponse
import dev.icerock.moko.mvvm.flow.cStateFlow
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class LoginState {
    data object Idle : LoginState()
    data object Loading : LoginState()
    data class Success(val data: AuthEntity) : LoginState()
    data class Error(val message: String) : LoginState()
}

class LoginViewModel(
    private val usecase: LoginUseCase
) : ViewModel() {

    private val _loginState = MutableStateFlow<LoginState>(LoginState.Idle)
    val loginState = _loginState.asStateFlow().cStateFlow()

    fun login(params: LoginFormRequestDto) {
        viewModelScope.launch {
            usecase.execute(params).collect { response ->
                when (response) {
                    is ApiResponse.Loading -> {
                        _loginState.value = LoginState.Loading
                    }

                    is ApiResponse.Success -> {
                        _loginState.value = LoginState.Success(response.data.data!!)
                    }

                    is ApiResponse.Failure -> {
                        _loginState.value = LoginState.Error(response.errorMessage)
                    }
                }
            }
        }
    }
}
