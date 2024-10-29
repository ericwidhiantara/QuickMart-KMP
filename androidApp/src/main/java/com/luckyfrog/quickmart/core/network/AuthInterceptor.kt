package com.luckyfrog.quickmart.core.network

import android.util.Log
import com.luckyfrog.quickmart.utils.TokenManager
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val tokenManager: TokenManager,
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = runBlocking {
            tokenManager.getToken()
        }
        val refreshToken = runBlocking {
            tokenManager.getRefreshToken()
        }

        Log.d("AuthInterceptor", "this is token: $token")
        Log.d("AuthInterceptor", "this refreshToken: $refreshToken")
        val request = chain.request().newBuilder()
        request.addHeader("Authorization", "Bearer $token")
        return chain.proceed(request.build())
    }
}