package com.luckyfrog.quickmart.features.auth.data.datasources.remote

import com.luckyfrog.quickmart.features.auth.data.models.request.ForgotPasswordChangePasswordFormRequestDto
import com.luckyfrog.quickmart.features.auth.data.models.request.ForgotPasswordSendOTPFormRequestDto
import com.luckyfrog.quickmart.features.auth.data.models.request.ForgotPasswordVerifyOTPFormRequestDto
import com.luckyfrog.quickmart.features.auth.data.models.request.LoginFormRequestDto
import com.luckyfrog.quickmart.features.auth.data.models.request.RegisterFormRequestDto
import com.luckyfrog.quickmart.features.auth.data.models.request.VerifyOTPFormRequestDto
import io.ktor.client.statement.HttpResponse

interface AuthRemoteDataSource {
    suspend fun login(params: LoginFormRequestDto): HttpResponse
    suspend fun register(params: RegisterFormRequestDto): HttpResponse
    suspend fun sendOTP(): HttpResponse
    suspend fun verifyOTP(params: VerifyOTPFormRequestDto): HttpResponse
    suspend fun forgotPasswordSendOTP(params: ForgotPasswordSendOTPFormRequestDto): HttpResponse
    suspend fun forgotPasswordVerifyOTP(params: ForgotPasswordVerifyOTPFormRequestDto): HttpResponse    suspend fun forgotPasswordChangePassword(params: ForgotPasswordChangePasswordFormRequestDto): HttpResponse
    suspend fun getUserLogin(): HttpResponse
}
