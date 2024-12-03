package com.luckyfrog.quickmart.features.auth.data.datasources.remote

import com.luckyfrog.quickmart.core.generic.dto.ResponseDto
import com.luckyfrog.quickmart.features.auth.data.models.response.AuthResponseDto
import com.luckyfrog.quickmart.features.auth.data.models.response.ForgotPasswordVerifyCodeResponseDto
import com.luckyfrog.quickmart.features.auth.data.models.response.UserResponseDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.contentType

interface AuthApi {

    suspend fun postLogin(
        username: String,
        password: String
    ): HttpResponse

    suspend fun postRegister(
        fullname: String,
        username: String,
        email: String,
        password: String,
        confirmPassword: String
    ): HttpResponse

    suspend fun postSendOTP(): HttpResponse

    suspend fun postVerifyOTP(
        otpCode: String
    ): HttpResponse

    suspend fun postForgotPasswordSendOTP(
        email: String
    ): HttpResponse

    suspend fun postForgotPasswordVerifyOTP(
        email: String,
        otpCode: String
    ): HttpResponse

    suspend fun postForgotPasswordChangePassword(
        otpId: String,
        newPassword: String,
        confirmPassword: String
    ): HttpResponse

    suspend fun getUserLogin(
        accessToken: String
    ): HttpResponse

    suspend fun postRefreshToken(
        refreshToken: String
    ): HttpResponse
}

class AuthApiImpl(private val client: HttpClient) : AuthApi {

    override suspend fun postLogin(username: String, password: String): HttpResponse {
        val response = client.post("auth/login") {
            setBody(MultiPartFormDataContent(
                formData {
                    append("username", username)
                    append("password", password)
                }
            ))
        }
        return response // Assuming ResponseDto is a wrapper around the response
    }

    override suspend fun postRegister(
        fullname: String,
        username: String,
        email: String,
        password: String,
        confirmPassword: String
    ): HttpResponse {
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
        return response

    }
    override suspend fun postSendOTP(): HttpResponse {
        val response = client.post("auth/verify-email/send-otp")
        return response
    }

    override suspend fun postVerifyOTP(otpCode: String): HttpResponse{
        val response = client.post("auth/verify-email/verify-otp") {
            setBody(MultiPartFormDataContent(
                formData {
                    append("otp_code", otpCode)
                }
            ))
        }
        return response
    }

    override suspend fun postForgotPasswordSendOTP(email: String): HttpResponse {
        val response = client.post("auth/forgot-password/send-otp") {
            setBody(MultiPartFormDataContent(
                formData {
                    append("email", email)
                }
            ))
        }
        return response
    }

    override suspend fun postForgotPasswordVerifyOTP(
        email: String,
        otpCode: String
    ): HttpResponse{
        val response = client.post("auth/forgot-password/verify-otp") {
            setBody(MultiPartFormDataContent(
                formData {
                    append("email", email)
                    append("otp_code", otpCode)
                }
            ))
        }
        return response
    }

    override suspend fun postForgotPasswordChangePassword(
        otpId: String,
        newPassword: String,
        confirmPassword: String
    ): HttpResponse {
        val response = client.post("auth/forgot-password/change-password") {
            setBody(MultiPartFormDataContent(
                formData {
                    append("otp_id", otpId)
                    append("new_password", newPassword)
                    append("confirm_password", confirmPassword)
                }
            ))
        }
        return response
    }

    override suspend fun getUserLogin(accessToken: String): HttpResponse {
        val response = client.post("auth/check-token") {
            setBody(MultiPartFormDataContent(
                formData {
                    append("access_token", accessToken)
                }
            ))
        }
        return response
    }

    override suspend fun postRefreshToken(refreshToken: String): HttpResponse {
        val response = client.post("auth/refresh-token") {
            contentType(ContentType.Application.FormUrlEncoded)
            setBody("refresh_token=$refreshToken")
        }
        return response
    }
}
