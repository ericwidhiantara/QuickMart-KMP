package com.luckyfrog.quickmart.features.auth.data.datasources.remote

import com.luckyfrog.quickmart.features.auth.data.models.response.LoginFormRequestDto
import com.luckyfrog.quickmart.features.auth.data.models.response.LoginResponseDto
import com.luckyfrog.quickmart.features.auth.data.models.response.RefreshTokenFormRequestDto
import com.luckyfrog.quickmart.features.auth.data.models.response.UserResponseDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AuthApi {

    @POST("auth/login")
    suspend fun postLogin(
        @Body params: LoginFormRequestDto

    ): Response<LoginResponseDto>

    @GET("auth/me")
    suspend fun getUserLogin(
    ): Response<UserResponseDto>

    @POST("auth/refresh")
    suspend fun postRefreshToken(
        @Body params: RefreshTokenFormRequestDto

    ): Response<LoginResponseDto>
}