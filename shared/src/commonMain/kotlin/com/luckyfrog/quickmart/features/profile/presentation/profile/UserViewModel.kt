package com.luckyfrog.quickmart.features.profile.presentation.profile

import com.luckyfrog.quickmart.features.auth.domain.entities.UserEntity
import com.luckyfrog.quickmart.features.profile.domain.entities.UpdateProfileParamsEntity
import com.luckyfrog.quickmart.features.profile.domain.usecases.DeleteAccountUseCase
import com.luckyfrog.quickmart.features.profile.domain.usecases.GetUserUseCase
import com.luckyfrog.quickmart.features.profile.domain.usecases.UpdateProfileUseCase
import com.luckyfrog.quickmart.utils.ApiResponse
import dev.icerock.moko.mvvm.flow.cStateFlow
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class UserState {
    data object Idle : UserState()
    data object Loading : UserState()
    data class Success(val data: UserEntity) : UserState()
    data class Error(val code: Int, val message: String) : UserState()
}

sealed class UpdateProfileState {
    data object Idle : UpdateProfileState()
    data object Loading : UpdateProfileState()
    data class Success(val data: UserEntity) : UpdateProfileState()
    data class Error(val message: String) : UpdateProfileState()
}

sealed class DeleteAccountState {
    data object Idle : DeleteAccountState()
    data object Loading : DeleteAccountState()
    data object Success : DeleteAccountState()
    data class Error(val message: String) : DeleteAccountState()
}

class UserViewModel(
    private val usecase: GetUserUseCase,
    private val updateProfileUseCase: UpdateProfileUseCase,
    private val deleteAccountUseCase: DeleteAccountUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<UserState>(UserState.Idle)
    val userState = _state.asStateFlow().cStateFlow()

    private val _updateProfileState = MutableStateFlow<UpdateProfileState>(UpdateProfileState.Idle)
    val updateProfileState = _updateProfileState.asStateFlow().cStateFlow()

    private val _deleteAccountState = MutableStateFlow<DeleteAccountState>(DeleteAccountState.Idle)
    val deleteAccountState = _deleteAccountState.asStateFlow().cStateFlow()

    fun getUserLogin() {
        viewModelScope.launch {
            usecase.execute(Unit).collect { response ->
                _state.value = when (response) {
                    is ApiResponse.Loading -> UserState.Loading
                    is ApiResponse.Success -> UserState.Success(response.data.data!!)
                    is ApiResponse.Failure -> UserState.Error(response.code, response.errorMessage)
                }
            }
        }
    }

    fun updateProfile(params: UpdateProfileParamsEntity) {
        viewModelScope.launch {
            updateProfileUseCase.execute(params).collect { response ->
                _updateProfileState.value = when (response) {
                    is ApiResponse.Loading -> UpdateProfileState.Loading
                    is ApiResponse.Success -> UpdateProfileState.Success(response.data.data!!)
                    is ApiResponse.Failure -> UpdateProfileState.Error(response.errorMessage)
                }
            }
        }
    }

    fun deleteAccount() {
        viewModelScope.launch {
            deleteAccountUseCase.execute(Unit).collect { response ->
                _deleteAccountState.value = when (response) {
                    is ApiResponse.Loading -> DeleteAccountState.Loading
                    is ApiResponse.Success -> DeleteAccountState.Success
                    is ApiResponse.Failure -> DeleteAccountState.Error(response.errorMessage)
                }
            }
        }
    }

    fun resetUpdateProfileState() { _updateProfileState.value = UpdateProfileState.Idle }
    fun resetDeleteAccountState() { _deleteAccountState.value = DeleteAccountState.Idle }
}
