package com.luckyfrog.quickmart.features.profile.presentation.check_password

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.luckyfrog.quickmart.core.generic.entities.MetaEntity
import com.luckyfrog.quickmart.core.generic.mapper.toEntity
import com.luckyfrog.quickmart.features.profile.data.models.request.CheckPasswordFormRequestDto
import com.luckyfrog.quickmart.features.profile.domain.usecases.CheckPasswordUseCase
import com.luckyfrog.quickmart.utils.helper.ApiResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class CheckPasswordState {
    data object Idle : CheckPasswordState()
    data object Loading : CheckPasswordState()
    data class Success(val data: MetaEntity) : CheckPasswordState()
    data class Error(val message: String) : CheckPasswordState()
}

@HiltViewModel
class CheckPasswordViewModel @Inject constructor(
    private val _usecase: CheckPasswordUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<CheckPasswordState>(CheckPasswordState.Idle)
    val state: StateFlow<CheckPasswordState> = _state

    fun checkPassword(params: CheckPasswordFormRequestDto) {
        viewModelScope.launch {
            _usecase.execute(params).collect { response ->
                when (response) {
                    is ApiResponse.Loading -> {
                        _state.value = CheckPasswordState.Loading
                    }

                    is ApiResponse.Success -> {
                        _state.value = CheckPasswordState.Success(response.data.meta?.toEntity()!!)
                    }

                    is ApiResponse.Failure -> {
                        _state.value = CheckPasswordState.Error(response.errorMessage)
                    }
                }
            }
        }
    }

}
