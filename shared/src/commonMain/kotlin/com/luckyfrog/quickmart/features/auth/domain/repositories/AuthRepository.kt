package com.luckyfrog.quickmart.features.auth.domain.repositories

import com.luckyfrog.quickmart.core.generic.dto.ResponseDto
import com.luckyfrog.quickmart.features.auth.data.models.request.ForgotPasswordChangePasswordFormRequestDto
import com.luckyfrog.quickmart.features.auth.data.models.request.ForgotPasswordSendOTPFormRequestDto
import com.luckyfrog.quickmart.features.auth.data.models.request.ForgotPasswordVerifyOTPFormRequestDto
import com.luckyfrog.quickmart.features.auth.data.models.request.LoginFormRequestDto
import com.luckyfrog.quickmart.features.auth.data.models.request.RegisterFormRequestDto
import com.luckyfrog.quickmart.features.auth.domain.entities.AuthEntity
import com.luckyfrog.quickmart.features.auth.domain.entities.ForgotPasswordVerifyCodeResponseEntity
import com.luckyfrog.quickmart.features.profile.data.models.request.VerifyOTPFormRequestDto
import com.luckyfrog.quickmart.utils.ApiResponse
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun login(params: LoginFormRequestDto): Flow<ApiResponse<ResponseDto<AuthEntity>>>

    suspend fun register(params: RegisterFormRequestDto): Flow<ApiResponse<ResponseDto<AuthEntity>>>
    
    suspend fun forgotPasswordSendOTP(params: ForgotPasswordSendOTPFormRequestDto): Flow<ApiResponse<ResponseDto<Unit>>>

    suspend fun forgotPasswordVerifyOTP(params: ForgotPasswordVerifyOTPFormRequestDto): Flow<ApiResponse<ResponseDto<ForgotPasswordVerifyCodeResponseEntity>>>

    suspend fun forgotPasswordChangePassword(params: ForgotPasswordChangePasswordFormRequestDto): Flow<ApiResponse<ResponseDto<Unit>>>
}
