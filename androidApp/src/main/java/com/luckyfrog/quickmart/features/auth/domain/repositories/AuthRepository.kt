package com.luckyfrog.quickmart.features.auth.domain.repositories

import com.luckyfrog.quickmart.core.generic.dto.ResponseDto
import com.luckyfrog.quickmart.features.auth.data.models.response.ForgotPasswordSendOTPFormRequestDto
import com.luckyfrog.quickmart.features.auth.data.models.response.ForgotPasswordVerifyOTPFormRequestDto
import com.luckyfrog.quickmart.features.auth.data.models.response.LoginFormRequestDto
import com.luckyfrog.quickmart.features.auth.data.models.response.RegisterFormRequestDto
import com.luckyfrog.quickmart.features.auth.data.models.response.VerifyOTPFormRequestDto
import com.luckyfrog.quickmart.features.auth.domain.entities.AuthEntity
import com.luckyfrog.quickmart.features.auth.domain.entities.ForgotPasswordVerifyCodeResponseEntity
import com.luckyfrog.quickmart.features.auth.domain.entities.UserEntity
import com.luckyfrog.quickmart.utils.helper.ApiResponse
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun login(params: LoginFormRequestDto): Flow<ApiResponse<ResponseDto<AuthEntity>>>

    suspend fun register(params: RegisterFormRequestDto): Flow<ApiResponse<ResponseDto<AuthEntity>>>

    suspend fun sendOTP(): Flow<ApiResponse<ResponseDto<Unit>>>

    suspend fun verifyOTP(params: VerifyOTPFormRequestDto): Flow<ApiResponse<ResponseDto<Unit>>>

    suspend fun forgotPasswordSendOTP(params: ForgotPasswordSendOTPFormRequestDto): Flow<ApiResponse<ResponseDto<Unit>>>

    suspend fun forgotPasswordVerifyOTP(params: ForgotPasswordVerifyOTPFormRequestDto): Flow<ApiResponse<ResponseDto<ForgotPasswordVerifyCodeResponseEntity>>>

    suspend fun getUserLogin(): Flow<ApiResponse<ResponseDto<UserEntity>>>
}
