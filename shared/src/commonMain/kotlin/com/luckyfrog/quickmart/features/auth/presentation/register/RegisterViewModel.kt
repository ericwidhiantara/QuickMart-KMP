package com.luckyfrog.quickmart.features.auth.presentation.register

import com.luckyfrog.quickmart.features.auth.data.models.request.RegisterFormRequestDto
import com.luckyfrog.quickmart.features.auth.domain.entities.AuthEntity
import com.luckyfrog.quickmart.features.auth.domain.usecases.RegisterUseCase
import com.luckyfrog.quickmart.utils.ApiResponse
import dev.icerock.moko.mvvm.flow.cStateFlow
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class RegisterState {
    data object Idle : RegisterState()
    data object Loading : RegisterState()
    data class Success(val data: AuthEntity) : RegisterState()
    data class Error(val message: String) : RegisterState()
}

class RegisterViewModel(
    private val _usecase: RegisterUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<RegisterState>(RegisterState.Idle)
    val state = _state.asStateFlow().cStateFlow()

    fun register(params: RegisterFormRequestDto) {
        viewModelScope.launch {
            _usecase.execute(params).collect { response ->
                when (response) {
                    is ApiResponse.Loading -> {
                        _state.value = RegisterState.Loading
                    }

                    is ApiResponse.Success -> {
                        _state.value = RegisterState.Success(response.data.data!!)
                    }

                    is ApiResponse.Failure -> {
                        _state.value = RegisterState.Error(response.errorMessage)
                    }
                }
            }
        }
    }
}
