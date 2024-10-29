package com.luckyfrog.quickmart.features.auth.domain.usecases

import android.util.Log
import com.luckyfrog.quickmart.core.generic.usecase.UseCase
import com.luckyfrog.quickmart.features.auth.domain.entities.UserEntity
import com.luckyfrog.quickmart.features.auth.domain.repositories.AuthRepository
import com.luckyfrog.quickmart.utils.helper.ApiResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetUserUseCase @Inject constructor(
    private val repository: AuthRepository
) : UseCase<Unit, Flow<ApiResponse<UserEntity>>> {

    override suspend fun execute(input: Unit): Flow<ApiResponse<UserEntity>> =
        flow {
            repository.getUserLogin().collect { response ->
                Log.d("GetUserUseCase", "response: ${response}")
                when (response) {

                    is ApiResponse.Loading -> emit(ApiResponse.Loading)

                    is ApiResponse.Success -> emit(ApiResponse.Success(response.data))
                    is ApiResponse.Failure -> emit(
                        ApiResponse.Failure(
                            response.errorMessage,
                            response.code
                        )
                    )
                }
            }
        }
}
