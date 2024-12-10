package com.luckyfrog.quickmart.core.app

import android.content.Context
import android.util.Log
import com.luckyfrog.quickmart.core.di.provideSettings
import com.luckyfrog.quickmart.core.preferences.StringSettingConfig
import com.luckyfrog.quickmart.utils.Constants.ACCESS_TOKEN
import com.luckyfrog.quickmart.utils.Constants.APP_THEME
import com.luckyfrog.quickmart.utils.Constants.FIRST_TIME
import com.luckyfrog.quickmart.utils.Constants.REFRESH_TOKEN
import com.luckyfrog.quickmart.utils.resource.theme.AppTheme
import com.russhwolf.settings.Settings
import com.russhwolf.settings.get

object AppPreferences {
    private lateinit var settings: Settings

    fun setTheme(value: AppTheme, context: Context): Boolean {
        settings = provideSettings(context)
        settings.putString(APP_THEME, value.name)
        return (settings.get<String>(APP_THEME) ?: AppTheme.Default.name) == value.name
    }

    fun getTheme(context: Context): AppTheme {
        settings = provideSettings(context)
        settings.putString(APP_THEME, AppTheme.Default.name)
        return AppTheme.valueOf(settings.get<String>(APP_THEME) ?: AppTheme.Default.name)
    }

    fun setFirstTime(context: Context): String {
        settings = provideSettings(context)
        val config =
            StringSettingConfig(settings, FIRST_TIME, "")
        config.set("1")
        Log.i("AppPreferences", "getFirstTime: ${config.get()}")

        return config.get()
    }

    fun getFirstTime(context: Context): String {
        settings = provideSettings(context)
        val config =
            StringSettingConfig(settings, FIRST_TIME, "")
        return config.get()
    }

    fun setToken(value: String, context: Context): String {
        settings = provideSettings(context)
        val configAccessToken =
            StringSettingConfig(settings, ACCESS_TOKEN, "")
        configAccessToken.set(value)
        return configAccessToken.get()
    }

    fun getToken(context: Context): String {
        settings = provideSettings(context)
        val config =
            StringSettingConfig(settings, ACCESS_TOKEN, "")
        return config.get()
    }

    fun setRefreshToken(value: String, context: Context): String {
        settings = provideSettings(context)
        val config =
            StringSettingConfig(settings, REFRESH_TOKEN, "")
        config.set(value)
        return config.get()
    }

    fun getRefreshToken(context: Context): String {
        settings = provideSettings(context)
        val configAccessToken =
            StringSettingConfig(settings, REFRESH_TOKEN, "")
        return configAccessToken.get()
    }

    fun clearToken(context: Context): Boolean {
        settings = provideSettings(context)
        val token =
            StringSettingConfig(settings, ACCESS_TOKEN, "")
        val refreshToken =
            StringSettingConfig(settings, REFRESH_TOKEN, "")
        token.remove()
        refreshToken.remove()
        return true
    }
}