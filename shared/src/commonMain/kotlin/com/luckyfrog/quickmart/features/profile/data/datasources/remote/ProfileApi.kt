package com.luckyfrog.quickmart.features.profile.data.datasources.remote

import com.luckyfrog.quickmart.core.generic.dto.ResponseDto
import com.luckyfrog.quickmart.features.auth.data.models.response.UserResponseDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody

interface ProfileApi {
    suspend fun getUserLogin(
    ): ResponseDto<UserResponseDto>

    suspend fun postCheckPassword(
        password: String,
    ): ResponseDto<Unit>

    suspend fun postChangePassword(
        newPassword: String,
        confirmPassword: String,
    ): ResponseDto<Unit>
}

class ProfileApiImpl(private val client: HttpClient) : ProfileApi {

    override suspend fun getUserLogin(): ResponseDto<UserResponseDto> {
        val response = client.get("user/me")
        return response.body()
    }

    override suspend fun postCheckPassword(password: String): ResponseDto<Unit> {
        val response = client.post("user/me/check-password") {
            setBody(MultiPartFormDataContent(
                formData {
                    append("password", password)
                }
            ))
        }
        println("response: $response")
        return response.body()
    }

    override suspend fun postChangePassword(
        newPassword: String,
        confirmPassword: String
    ): ResponseDto<Unit> {
        val response = client.post("user/me/password") {
            setBody(MultiPartFormDataContent(
                formData {
                    append("new_password", newPassword)
                    append("cconfirm_password", confirmPassword)
                }
            ))
        }
        println("response: $response")
        return response.body()
    }

}