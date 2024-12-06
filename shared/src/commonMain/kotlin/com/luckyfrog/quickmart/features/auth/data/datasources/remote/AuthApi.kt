package com.luckyfrog.quickmart.features.auth.data.datasources.remote

import com.luckyfrog.quickmart.core.generic.dto.ResponseDto
import com.luckyfrog.quickmart.features.auth.data.models.response.AuthResponseDto
import com.luckyfrog.quickmart.features.auth.data.models.response.ForgotPasswordVerifyCodeResponseDto
import com.luckyfrog.quickmart.features.auth.data.models.response.UserResponseDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

interface AuthApi {

    suspend fun postLogin(
        username: String,
        password: String
    ): ResponseDto<AuthResponseDto>

    suspend fun postRegister(
        fullname: String,
        username: String,
        email: String,
        password: String,
        confirmPassword: String
    ): ResponseDto<AuthResponseDto>

    suspend fun postSendOTP(): ResponseDto<Unit>

    suspend fun postVerifyOTP(
        otpCode: String
    ): ResponseDto<Unit>

    suspend fun postForgotPasswordSendOTP(
        email: String
    ): ResponseDto<Unit>

    suspend fun postForgotPasswordVerifyOTP(
        email: String,
        otpCode: String
    ): ResponseDto<ForgotPasswordVerifyCodeResponseDto>

    suspend fun postForgotPasswordChangePassword(
        otpId: String,
        newPassword: String,
        confirmPassword: String
    ): ResponseDto<Unit>

    suspend fun getUserLogin(
    ): ResponseDto<UserResponseDto>

    suspend fun postRefreshToken(
        refreshToken: String
    ): ResponseDto<AuthResponseDto>
}

class AuthApiImpl(private val client: HttpClient) : AuthApi {

    override suspend fun postLogin(
        username: String,
        password: String
    ): ResponseDto<AuthResponseDto> {
        val response = client.post("auth/login") {
            setBody(MultiPartFormDataContent(
                formData {
                    append("username", username)
                    append("password", password)
                }
            ))
        }
        println("postLogin response: $response")
        return response.body() // Assuming ResponseDto is a wrapper around the response
    }

    override suspend fun postRegister(
        fullname: String,
        username: String,
        email: String,
        password: String,
        confirmPassword: String
    ): ResponseDto<AuthResponseDto> {
        val response = client.post("auth/register") {
            setBody(MultiPartFormDataContent(
                formData {
                    append("fullname", fullname)
                    append("username", username)
                    append("email", email)
                    append("password", password)
                    append("confirm_password", confirmPassword)
                }
            ))
        }
        return response.body()

    }

    override suspend fun postSendOTP(): ResponseDto<Unit> {
        val response = client.post("auth/verify-email/send-otp")
        return response.body()
    }

    override suspend fun postVerifyOTP(otpCode: String): ResponseDto<Unit> {
        val response = client.post("auth/verify-email/verify-otp") {
            setBody(MultiPartFormDataContent(
                formData {
                    append("otp_code", otpCode)
                }
            ))
        }
        return response.body()
    }

    override suspend fun postForgotPasswordSendOTP(email: String): ResponseDto<Unit> {
        val response = client.post("auth/forgot-password/send-otp") {
            setBody(MultiPartFormDataContent(
                formData {
                    append("email", email)
                }
            ))
        }
        return response.body()
    }

    override suspend fun postForgotPasswordVerifyOTP(
        email: String,
        otpCode: String
    ): ResponseDto<ForgotPasswordVerifyCodeResponseDto> {
        val response = client.post("auth/forgot-password/verify-otp") {
            setBody(MultiPartFormDataContent(
                formData {
                    append("email", email)
                    append("otp_code", otpCode)
                }
            ))
        }
        return response.body()
    }

    override suspend fun postForgotPasswordChangePassword(
        otpId: String,
        newPassword: String,
        confirmPassword: String
    ): ResponseDto<Unit> {
        val response = client.post("auth/forgot-password/change-password") {
            setBody(MultiPartFormDataContent(
                formData {
                    append("otp_id", otpId)
                    append("new_password", newPassword)
                    append("confirm_password", confirmPassword)
                }
            ))
        }
        return response.body()
    }

    override suspend fun getUserLogin(): ResponseDto<UserResponseDto> {
        val response = client.get("user/me")
        return response.body()
    }

    override suspend fun postRefreshToken(refreshToken: String): ResponseDto<AuthResponseDto> {
        val response = client.post("auth/refresh-token") {
            contentType(ContentType.Application.FormUrlEncoded)
            setBody("refresh_token=$refreshToken")
        }
        return response.body()
    }
}
