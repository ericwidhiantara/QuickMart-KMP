package com.luckyfrog.quickmart.core.network

import android.util.Log
import com.luckyfrog.quickmart.core.generic.dto.ResponseDto
import com.luckyfrog.quickmart.features.auth.data.datasources.remote.AuthApi
import com.luckyfrog.quickmart.features.auth.data.models.response.AuthResponseDto
import com.luckyfrog.quickmart.features.auth.domain.entities.RefreshTokenFormParamsEntity
import com.luckyfrog.quickmart.utils.TokenManager
import com.luckyfrog.quickmart.utils.helper.Constants
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import okhttp3.Route
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

class AuthAuthenticator @Inject constructor(
    private val tokenManager: TokenManager,
) : Authenticator {

    override fun authenticate(route: Route?, response: Response): Request? {
        val token = runBlocking {
            tokenManager.getRefreshToken()
        }



        return runBlocking {
            Log.d("AuthAuthenticator", "Ini token: $token")

            val newToken = getNewToken(token)

            if (!newToken.isSuccessful || newToken.body()?.data?.accessToken == "") { //Couldn't refresh the token, so restart the login process
                tokenManager.clearTokens()
            }

            newToken.body()?.let {
                tokenManager.saveToken(it.data?.accessToken ?: "")
//                tokenManager.saveRefreshToken(it.data?.refreshToken ?: "")
                response.request.newBuilder()
                    .header("Authorization", "Bearer ${it.data?.accessToken ?: ""}")
                    .build()
            }
        }
    }

    private suspend fun getNewToken(refreshToken: String?): retrofit2.Response<ResponseDto<AuthResponseDto>> {
//        val loggingInterceptor = HttpLoggingInterceptor()
//        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        val okHttpClient = OkHttpClient.Builder()
//            .addInterceptor(loggingInterceptor)
            .addInterceptor(RetrofitInterceptor())
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.SERVER_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
        val service = retrofit.create(AuthApi::class.java)
        val form = RefreshTokenFormParamsEntity(
            refreshToken = refreshToken ?: "",
        )
        Log.i("AuthAuthenticator", "refresh token: ${form.refreshToken}")

        return service.postRefreshToken(form.refreshToken.toRequestBody())
    }
}