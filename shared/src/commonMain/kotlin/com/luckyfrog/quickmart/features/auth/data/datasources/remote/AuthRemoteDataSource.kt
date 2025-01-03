package com.luckyfrog.quickmart.features.auth.data.datasources.remote

import com.luckyfrog.quickmart.core.generic.dto.ResponseDto
import com.luckyfrog.quickmart.features.auth.data.models.request.ForgotPasswordChangePasswordFormRequestDto
import com.luckyfrog.quickmart.features.auth.data.models.request.ForgotPasswordSendOTPFormRequestDto
import com.luckyfrog.quickmart.features.auth.data.models.request.ForgotPasswordVerifyOTPFormRequestDto
import com.luckyfrog.quickmart.features.auth.data.models.request.LoginFormRequestDto
import com.luckyfrog.quickmart.features.auth.data.models.request.RegisterFormRequestDto
import com.luckyfrog.quickmart.features.auth.data.models.response.AuthResponseDto
import com.luckyfrog.quickmart.features.auth.data.models.response.ForgotPasswordVerifyCodeResponseDto

interface AuthRemoteDataSource {
    suspend fun login(params: LoginFormRequestDto): ResponseDto<AuthResponseDto>
    suspend fun register(params: RegisterFormRequestDto): ResponseDto<AuthResponseDto>
    suspend fun forgotPasswordSendOTP(params: ForgotPasswordSendOTPFormRequestDto): ResponseDto<Unit>
    suspend fun forgotPasswordVerifyOTP(params: ForgotPasswordVerifyOTPFormRequestDto): ResponseDto<ForgotPasswordVerifyCodeResponseDto>
    suspend fun forgotPasswordChangePassword(params: ForgotPasswordChangePasswordFormRequestDto): ResponseDto<Unit>
}
