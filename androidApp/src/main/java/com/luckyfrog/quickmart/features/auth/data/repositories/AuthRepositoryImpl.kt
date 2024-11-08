package com.luckyfrog.quickmart.features.auth.data.repositories

import com.luckyfrog.quickmart.core.generic.dto.ResponseDto
import com.luckyfrog.quickmart.core.network.processResponse
import com.luckyfrog.quickmart.features.auth.data.datasources.remote.AuthRemoteDataSource
import com.luckyfrog.quickmart.features.auth.data.models.mapper.toEntity
import com.luckyfrog.quickmart.features.auth.data.models.response.ForgotPasswordSendOTPFormRequestDto
import com.luckyfrog.quickmart.features.auth.data.models.response.ForgotPasswordVerifyOTPFormRequestDto
import com.luckyfrog.quickmart.features.auth.data.models.response.LoginFormRequestDto
import com.luckyfrog.quickmart.features.auth.data.models.response.RegisterFormRequestDto
import com.luckyfrog.quickmart.features.auth.data.models.response.VerifyOTPFormRequestDto
import com.luckyfrog.quickmart.features.auth.domain.entities.AuthEntity
import com.luckyfrog.quickmart.features.auth.domain.entities.ForgotPasswordVerifyCodeResponseEntity
import com.luckyfrog.quickmart.features.auth.domain.entities.UserEntity
import com.luckyfrog.quickmart.features.auth.domain.repositories.AuthRepository
import com.luckyfrog.quickmart.utils.helper.ApiResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val remoteDataSource: AuthRemoteDataSource
) : AuthRepository {

    override suspend fun login(params: LoginFormRequestDto): Flow<ApiResponse<ResponseDto<AuthEntity>>> =
        flow {
            emit(ApiResponse.Loading)
            val response = remoteDataSource.login(params)
            emit(processResponse(response) { it.data?.toEntity() })
        }

    override suspend fun register(params: RegisterFormRequestDto): Flow<ApiResponse<ResponseDto<AuthEntity>>> =
        flow {
            emit(ApiResponse.Loading)
            val response = remoteDataSource.register(params)
            emit(processResponse(response) { it.data?.toEntity() })
        }

    override suspend fun sendOTP(): Flow<ApiResponse<ResponseDto<Unit>>> = flow {
        emit(ApiResponse.Loading)
        val response = remoteDataSource.sendOTP()
        emit(processResponse(response) { })
    }

    override suspend fun verifyOTP(params: VerifyOTPFormRequestDto): Flow<ApiResponse<ResponseDto<Unit>>> =
        flow {
            emit(ApiResponse.Loading)
            val response = remoteDataSource.verifyOTP(params)
            emit(processResponse(response) { })
        }

    override suspend fun forgotPasswordSendOTP(params: ForgotPasswordSendOTPFormRequestDto): Flow<ApiResponse<ResponseDto<Unit>>> =
        flow {
            emit(ApiResponse.Loading)
            val response = remoteDataSource.forgotPasswordSendOTP(params)
            emit(processResponse(response) { })
        }

    override suspend fun forgotPasswordVerifyOTP(params: ForgotPasswordVerifyOTPFormRequestDto): Flow<ApiResponse<ResponseDto<ForgotPasswordVerifyCodeResponseEntity>>> =
        flow {
            emit(ApiResponse.Loading)
            val response = remoteDataSource.forgotPasswordVerifyOTP(params)
            emit(processResponse(response) { it.data?.toEntity() })
        }

    override suspend fun getUserLogin(): Flow<ApiResponse<ResponseDto<UserEntity>>> = flow {
        emit(ApiResponse.Loading)
        val response = remoteDataSource.getUserLogin()
        emit(processResponse(response) { it.data?.toEntity() })
    }
}
