package com.luckyfrog.quickmart.utils


import android.content.Context
import com.luckyfrog.quickmart.core.app.AppPreferences.ACCESS_TOKEN
import com.luckyfrog.quickmart.core.app.AppPreferences.REFRESH_TOKEN
import io.paperdb.Paper

class TokenManager(private val context: Context) {
    // In-memory cache for tokens
    private var cachedAccessToken: String? = null
    private var cachedRefreshToken: String? = null

    companion object {
        private const val TOKEN_KEY = ACCESS_TOKEN
        private const val REFRESH_TOKEN_KEY = REFRESH_TOKEN
    }

    @Synchronized
    fun getToken(): String? {
        if (cachedAccessToken == null) {
            cachedAccessToken = Paper.book().read<String>(TOKEN_KEY, "")
        }
        return cachedAccessToken
    }

    @Synchronized
    fun saveToken(token: String) {
        cachedAccessToken = token
        Paper.book().write(TOKEN_KEY, token)
    }

    @Synchronized
    fun getRefreshToken(): String? {
        if (cachedRefreshToken == null) {
            cachedRefreshToken = Paper.book().read<String>(REFRESH_TOKEN_KEY, "")
        }
        return cachedRefreshToken
    }

    @Synchronized
    fun saveRefreshToken(refreshToken: String) {
        cachedRefreshToken = refreshToken
        Paper.book().write(REFRESH_TOKEN_KEY, refreshToken)
    }

    @Synchronized
    fun clearTokens() {
        cachedAccessToken = null
        cachedRefreshToken = null
        Paper.book().delete(TOKEN_KEY)
        Paper.book().delete(REFRESH_TOKEN_KEY)
    }
}