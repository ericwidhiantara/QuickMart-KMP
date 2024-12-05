package com.luckyfrog.quickmart.features.profile.presentation.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.luckyfrog.quickmart.features.auth.domain.entities.UserEntity
import com.luckyfrog.quickmart.features.auth.domain.usecases.GetUserUseCase
import com.luckyfrog.quickmart.utils.ApiResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class UserState {
    data object Idle : UserState()
    data object Loading : UserState()
    data class Success(val data: UserEntity) : UserState()
    data class Error(val code: Int, val message: String) : UserState()
}

class UserViewModel(
    private val usecase: GetUserUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<UserState>(UserState.Idle)
    val userState: StateFlow<UserState> = _state

    fun getUserLogin() {
        viewModelScope.launch {
            usecase.execute(Unit).collect { response ->
                when (response) {
                    is ApiResponse.Loading -> {
                        _state.value = UserState.Loading
                    }

                    is ApiResponse.Success -> {
                        _state.value = UserState.Success(response.data.data!!)
                    }

                    is ApiResponse.Failure -> {
                        _state.value = UserState.Error(response.code, response.errorMessage)
                    }
                }
            }
        }
    }
}
