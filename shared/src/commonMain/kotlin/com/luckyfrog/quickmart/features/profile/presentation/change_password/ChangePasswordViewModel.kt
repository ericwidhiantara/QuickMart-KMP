package com.luckyfrog.quickmart.features.profile.presentation.change_password

import com.luckyfrog.quickmart.core.generic.entities.MetaEntity
import com.luckyfrog.quickmart.core.generic.mapper.toEntity
import com.luckyfrog.quickmart.features.profile.data.models.request.ChangePasswordFormRequestDto
import com.luckyfrog.quickmart.features.profile.domain.usecases.ChangePasswordUseCase
import com.luckyfrog.quickmart.utils.ApiResponse
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class ChangePasswordState {
    data object Idle : ChangePasswordState()
    data object Loading : ChangePasswordState()
    data class Success(val data: MetaEntity) : ChangePasswordState()
    data class Error(val message: String) : ChangePasswordState()
}

class ChangePasswordViewModel(
    private val _usecase: ChangePasswordUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<ChangePasswordState>(ChangePasswordState.Idle)
    val state: StateFlow<ChangePasswordState> = _state

    fun changePassword(params: ChangePasswordFormRequestDto) {
        viewModelScope.launch {
            _usecase.execute(params).collect { response ->
                when (response) {
                    is ApiResponse.Loading -> {
                        _state.value = ChangePasswordState.Loading
                    }

                    is ApiResponse.Success -> {
                        _state.value = ChangePasswordState.Success(response.data.meta?.toEntity()!!)
                    }

                    is ApiResponse.Failure -> {
                        _state.value = ChangePasswordState.Error(response.errorMessage)
                    }
                }
            }
        }
    }

}
