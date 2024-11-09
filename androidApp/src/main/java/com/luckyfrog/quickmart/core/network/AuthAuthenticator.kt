package com.luckyfrog.quickmart.core.network

import com.luckyfrog.quickmart.core.generic.dto.ResponseDto
import com.luckyfrog.quickmart.features.auth.data.datasources.remote.AuthApi
import com.luckyfrog.quickmart.features.auth.data.models.response.AuthResponseDto
import com.luckyfrog.quickmart.utils.TokenManager
import com.luckyfrog.quickmart.utils.helper.Constants
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class AuthAuthenticator @Inject constructor(
    private val tokenManager: TokenManager,
) : Authenticator {
    private val lock = Any()

    override fun authenticate(route: Route?, response: Response): Request? {
        synchronized(lock) {
            // Don't retry if we already tried to refresh the token
            if (response.request.header("Retry-With-Refresh-Token") != null) {
                return null
            }

            val refreshToken = tokenManager.getRefreshToken()
            if (refreshToken.isNullOrEmpty()) {
                tokenManager.clearTokens()
                return null
            }

            return try {
                // Execute refresh token synchronously
                val newTokenResponse = runBlocking {
                    getNewToken(refreshToken)
                }

                if (!newTokenResponse.isSuccessful || newTokenResponse.body()?.data?.accessToken.isNullOrEmpty()) {
                    tokenManager.clearTokens()
                    return null
                }

                newTokenResponse.body()?.data?.let { authData ->
                    // Save new tokens
                    tokenManager.saveToken(authData.accessToken ?: "")
                    authData.refreshToken?.let { newRefreshToken ->
                        tokenManager.saveRefreshToken(newRefreshToken)
                    }

                    // Retry the original request with the new token
                    response.request.newBuilder()
                        .removeHeader("Authorization")
                        .addHeader("Authorization", "Bearer ${authData.accessToken}")
                        .header("Retry-With-Refresh-Token", "true")
                        .build()
                }
            } catch (e: Exception) {
                tokenManager.clearTokens()
                null
            }
        }
    }

    private suspend fun getNewToken(refreshToken: String): retrofit2.Response<ResponseDto<AuthResponseDto>> {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(RetrofitInterceptor())
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.SERVER_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()

        val service = retrofit.create(AuthApi::class.java)
        return service.postRefreshToken(refreshToken)
    }
}