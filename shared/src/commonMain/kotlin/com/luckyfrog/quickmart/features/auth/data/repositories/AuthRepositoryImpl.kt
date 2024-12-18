package com.luckyfrog.quickmart.features.auth.data.repositories

import com.luckyfrog.quickmart.core.generic.dto.ResponseDto
import com.luckyfrog.quickmart.core.network.processResponse
import com.luckyfrog.quickmart.features.auth.data.datasources.remote.AuthRemoteDataSource
import com.luckyfrog.quickmart.features.auth.data.models.mapper.toEntity
import com.luckyfrog.quickmart.features.auth.data.models.request.ForgotPasswordChangePasswordFormRequestDto
import com.luckyfrog.quickmart.features.auth.data.models.request.ForgotPasswordSendOTPFormRequestDto
import com.luckyfrog.quickmart.features.auth.data.models.request.ForgotPasswordVerifyOTPFormRequestDto
import com.luckyfrog.quickmart.features.auth.data.models.request.LoginFormRequestDto
import com.luckyfrog.quickmart.features.auth.data.models.request.RegisterFormRequestDto
import com.luckyfrog.quickmart.features.auth.domain.entities.AuthEntity
import com.luckyfrog.quickmart.features.auth.domain.entities.ForgotPasswordVerifyCodeResponseEntity
import com.luckyfrog.quickmart.features.auth.domain.repositories.AuthRepository
import com.luckyfrog.quickmart.features.profile.data.models.request.VerifyOTPFormRequestDto
import com.luckyfrog.quickmart.utils.ApiResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AuthRepositoryImpl(
    private val remoteDataSource: AuthRemoteDataSource
) : AuthRepository {

    override suspend fun login(params: LoginFormRequestDto): Flow<ApiResponse<ResponseDto<AuthEntity>>> =
        flow {
            emit(ApiResponse.Loading)
            val response = remoteDataSource.login(params)
            println("AuthRepositoryImpl response: $response")
            emit(processResponse(response) { it.data?.toEntity() })
        }

    override suspend fun register(params: RegisterFormRequestDto): Flow<ApiResponse<ResponseDto<AuthEntity>>> =
        flow {
            emit(ApiResponse.Loading)
            val response = remoteDataSource.register(params)
            emit(processResponse(response) { it.data?.toEntity() })
        }
    
    override suspend fun forgotPasswordSendOTP(params: ForgotPasswordSendOTPFormRequestDto): Flow<ApiResponse<ResponseDto<Unit>>> =
        flow {
            emit(ApiResponse.Loading)
            val response = remoteDataSource.forgotPasswordSendOTP(params)
            emit(processResponse(response) { it.data })
        }

    override suspend fun forgotPasswordVerifyOTP(params: ForgotPasswordVerifyOTPFormRequestDto): Flow<ApiResponse<ResponseDto<ForgotPasswordVerifyCodeResponseEntity>>> =
        flow {
            emit(ApiResponse.Loading)
            val response = remoteDataSource.forgotPasswordVerifyOTP(params)
            emit(processResponse(response) { it.data?.toEntity() })
        }

    override suspend fun forgotPasswordChangePassword(params: ForgotPasswordChangePasswordFormRequestDto): Flow<ApiResponse<ResponseDto<Unit>>> =
        flow {
            emit(ApiResponse.Loading)
            val response = remoteDataSource.forgotPasswordChangePassword(params)
            emit(processResponse(response) { it.data })
        }

}
