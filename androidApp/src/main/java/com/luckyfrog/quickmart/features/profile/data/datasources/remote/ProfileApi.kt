package com.luckyfrog.quickmart.features.profile.data.datasources.remote

import com.luckyfrog.quickmart.core.generic.dto.ResponseDto
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Part

interface ProfileApi {

    @Multipart
    @POST("user/me/check-password")
    suspend fun postCheckPassword(
        @Part("password") password: RequestBody,
    ): Response<ResponseDto<Unit>>

    @Multipart
    @PATCH("user/me/password")
    suspend fun postChangePassword(
        @Part("new_password") newPassword: RequestBody,
        @Part("confirm_password") confirmPassword: RequestBody
    ): Response<ResponseDto<Unit>>
}