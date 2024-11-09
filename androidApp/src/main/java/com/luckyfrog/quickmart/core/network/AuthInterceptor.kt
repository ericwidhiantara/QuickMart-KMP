package com.luckyfrog.quickmart.core.network

import com.luckyfrog.quickmart.utils.TokenManager
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val tokenManager: TokenManager
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = runBlocking {
            tokenManager.getToken()
        }

        val originalRequest = chain.request()

        // Skip adding the Authorization header for authentication endpoints
        // or if the request contains the No-Auth header
        if (originalRequest.url.encodedPath.contains("/auth") ||
            originalRequest.header("No-Auth") == "true"
        ) {
            return chain.proceed(originalRequest)
        }

        // Add the Authorization header if we have a token
        val request = if (!token.isNullOrEmpty()) {
            originalRequest.newBuilder()
                .header("Authorization", "Bearer $token")
                .build()
        } else {
            originalRequest
        }

        return chain.proceed(request)
    }
}