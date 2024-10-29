package com.luckyfrog.quickmart.utils


import android.content.Context
import com.luckyfrog.quickmart.core.app.AppPreferences.ACCESS_TOKEN
import com.luckyfrog.quickmart.core.app.AppPreferences.REFRESH_TOKEN
import io.paperdb.Paper

class TokenManager(private val context: Context) {
    companion object {
        private const val TOKEN_KEY = ACCESS_TOKEN
        private const val REFRESH_TOKEN_KEY = REFRESH_TOKEN
    }

    // Access Token Management
    fun getToken(): String? {
        return Paper.book().read(TOKEN_KEY, "")
    }

    fun saveToken(token: String) {
        Paper.book().write(TOKEN_KEY, token)
    }

    // Refresh Token Management
    fun getRefreshToken(): String? {
        return Paper.book().read(REFRESH_TOKEN_KEY, "")
    }

    fun saveRefreshToken(refreshToken: String) {
        Paper.book().write(REFRESH_TOKEN_KEY, refreshToken)
    }

    // Optional: Delete both tokens
    fun clearTokens() {
        Paper.book().delete(TOKEN_KEY)
        Paper.book().delete(REFRESH_TOKEN_KEY)
    }
}
