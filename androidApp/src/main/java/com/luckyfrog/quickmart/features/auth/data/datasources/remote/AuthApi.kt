package com.luckyfrog.quickmart.features.auth.data.datasources.remote

import com.luckyfrog.quickmart.core.generic.dto.ResponseDto
import com.luckyfrog.quickmart.features.auth.data.models.response.AuthResponseDto
import com.luckyfrog.quickmart.features.auth.data.models.response.ForgotPasswordVerifyCodeResponseDto
import com.luckyfrog.quickmart.features.auth.data.models.response.UserResponseDto
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface AuthApi {

    @Multipart
    @POST("auth/login")
    suspend fun postLogin(
        @Part("username") username: RequestBody,
        @Part("password") password: RequestBody
    ): Response<ResponseDto<AuthResponseDto>>

    @Multipart
    @POST("auth/register")
    suspend fun postRegister(
        @Part("fullname") fullname: RequestBody,
        @Part("username") username: RequestBody,
        @Part("email") email: RequestBody,
        @Part("password") password: RequestBody,
        @Part("confirm_password") confirmPassword: RequestBody
    ): Response<ResponseDto<AuthResponseDto>>

    @POST("auth/verify-email/send-otp")
    suspend fun postSendOTP(
    ): Response<ResponseDto<Unit>>

    @Multipart
    @POST("auth/verify-email/verify-otp")
    suspend fun postVerifyOTP(
        @Part("otp_code") otpCode: RequestBody
    ): Response<ResponseDto<Unit>>

    @Multipart
    @POST("auth/forgot-password/send-otp")
    suspend fun postForgotPasswordSendOTP(
        @Part("email") email: RequestBody
    ): Response<ResponseDto<Unit>>

    @Multipart
    @POST("auth/forgot-password/verify-otp")
    suspend fun postForgotPasswordVerifyOTP(
        @Part("email") email: RequestBody,
        @Part("otp_code") otpCode: RequestBody
    ): Response<ResponseDto<ForgotPasswordVerifyCodeResponseDto>>

    @Multipart
    @POST("auth/forgot-password/change-password")
    suspend fun postForgotPasswordChangePassword(
        @Part("otp_id") otpId: RequestBody,
        @Part("new_password") newPassword: RequestBody,
        @Part("confirm_password") confirmPassword: RequestBody
    ): Response<ResponseDto<Unit>>

    @Multipart
    @POST("auth/check-token")
    suspend fun getUserLogin(
        @Part("access_token") accessToken: RequestBody
    ): Response<ResponseDto<UserResponseDto>>

    @Multipart
    @POST("auth/refresh")
    suspend fun postRefreshToken(
        @Part("refresh_token") refreshToken: RequestBody
    ): Response<ResponseDto<AuthResponseDto>>
}