package com.luckyfrog.quickmart.features.auth.data.datasources.remote

import com.luckyfrog.quickmart.core.generic.dto.ResponseDto
import com.luckyfrog.quickmart.features.auth.data.models.response.LoginResponseDto
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
    ): Response<ResponseDto<LoginResponseDto>>

    @Multipart
    @POST("auth/check-token")
    suspend fun getUserLogin(
        @Part("access_token") accessToken: RequestBody
    ): Response<ResponseDto<UserResponseDto>>

    @Multipart
    @POST("auth/refresh")
    suspend fun postRefreshToken(
        @Part("refresh_token") refreshToken: RequestBody
    ): Response<ResponseDto<LoginResponseDto>>
}