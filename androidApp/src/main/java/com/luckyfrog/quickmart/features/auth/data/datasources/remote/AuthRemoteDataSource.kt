package com.luckyfrog.quickmart.features.auth.data.datasources.remote

import com.luckyfrog.quickmart.core.generic.dto.ResponseDto
import com.luckyfrog.quickmart.features.auth.data.models.request.ForgotPasswordChangePasswordFormRequestDto
import com.luckyfrog.quickmart.features.auth.data.models.request.ForgotPasswordSendOTPFormRequestDto
import com.luckyfrog.quickmart.features.auth.data.models.request.ForgotPasswordVerifyOTPFormRequestDto
import com.luckyfrog.quickmart.features.auth.data.models.request.LoginFormRequestDto
import com.luckyfrog.quickmart.features.auth.data.models.request.RegisterFormRequestDto
import com.luckyfrog.quickmart.features.auth.data.models.request.VerifyOTPFormRequestDto
import com.luckyfrog.quickmart.features.auth.data.models.response.AuthResponseDto
import com.luckyfrog.quickmart.features.auth.data.models.response.ForgotPasswordVerifyCodeResponseDto
import com.luckyfrog.quickmart.features.auth.data.models.response.UserResponseDto
import retrofit2.Response

interface AuthRemoteDataSource {
    suspend fun login(params: LoginFormRequestDto): Response<ResponseDto<AuthResponseDto>>

    suspend fun register(params: RegisterFormRequestDto): Response<ResponseDto<AuthResponseDto>>

    suspend fun sendOTP(): Response<ResponseDto<Unit>>

    suspend fun verifyOTP(params: VerifyOTPFormRequestDto): Response<ResponseDto<Unit>>

    suspend fun forgotPasswordSendOTP(params: ForgotPasswordSendOTPFormRequestDto): Response<ResponseDto<Unit>>

    suspend fun forgotPasswordVerifyOTP(params: ForgotPasswordVerifyOTPFormRequestDto): Response<ResponseDto<ForgotPasswordVerifyCodeResponseDto>>

    suspend fun forgotPasswordChangePassword(params: ForgotPasswordChangePasswordFormRequestDto): Response<ResponseDto<Unit>>

    suspend fun getUserLogin(): Response<ResponseDto<UserResponseDto>>
}
