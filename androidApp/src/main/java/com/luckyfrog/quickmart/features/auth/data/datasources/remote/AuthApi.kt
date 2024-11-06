package com.luckyfrog.quickmart.features.auth.data.datasources.remote

import com.luckyfrog.quickmart.core.generic.dto.ResponseDto
import com.luckyfrog.quickmart.features.auth.data.models.response.LoginFormRequestDto
import com.luckyfrog.quickmart.features.auth.data.models.response.LoginResponseDto
import com.luckyfrog.quickmart.features.auth.data.models.response.UserResponseDto
import com.luckyfrog.quickmart.features.auth.domain.entities.CheckTokenFormParamsEntity
import com.luckyfrog.quickmart.features.auth.domain.entities.RefreshTokenFormParamsEntity
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {

    @POST("auth/login")
    suspend fun postLogin(
        @Body params: LoginFormRequestDto
    ): Response<ResponseDto<LoginResponseDto>>

    @POST("auth/check-token")
    suspend fun getUserLogin(
        @Body params: CheckTokenFormParamsEntity
    ): Response<ResponseDto<UserResponseDto>>

    @POST("auth/refresh")
    suspend fun postRefreshToken(
        @Body params: RefreshTokenFormParamsEntity
    ): Response<ResponseDto<LoginResponseDto>>
}