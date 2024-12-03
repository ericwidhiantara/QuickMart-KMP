package com.luckyfrog.quickmart.utils

import com.russhwolf.settings.Settings
import com.russhwolf.settings.get
import com.russhwolf.settings.set
import kotlin.jvm.Synchronized

class TokenManager(private val settings: Settings) {

    // In-memory cache for tokens
    private var cachedAccessToken: String? = null
    private var cachedRefreshToken: String? = null

    companion object {
        private const val TOKEN_KEY = "AccessToken"
        private const val REFRESH_TOKEN_KEY = "RefreshToken"
    }

    @Synchronized
    fun getToken(): String? {
        if (cachedAccessToken == null) {
            cachedAccessToken = settings.getString(TOKEN_KEY, "")
        }
        return cachedAccessToken
    }

    @Synchronized
    fun saveToken(token: String) {
        cachedAccessToken = token
        settings[TOKEN_KEY] = token
    }

    @Synchronized
    fun getRefreshToken(): String? {
        if (cachedRefreshToken == null) {
            cachedRefreshToken = settings.getString(REFRESH_TOKEN_KEY, "")
        }
        return cachedRefreshToken
    }

    @Synchronized
    fun saveRefreshToken(refreshToken: String) {
        cachedRefreshToken = refreshToken
        settings[REFRESH_TOKEN_KEY] = refreshToken
    }

    @Synchronized
    fun clearTokens() {
        cachedAccessToken = null
        cachedRefreshToken = null
        settings.remove(TOKEN_KEY)
        settings.remove(REFRESH_TOKEN_KEY)
    }
}