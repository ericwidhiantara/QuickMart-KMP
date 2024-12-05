package com.luckyfrog.quickmart.core.app

import android.content.Context
import androidx.compose.ui.platform.LocalContext
import com.luckyfrog.quickmart.core.di.provideSettings
import com.luckyfrog.quickmart.utils.Constants.APP_THEME
import com.luckyfrog.quickmart.utils.Constants.ACCESS_TOKEN
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

    fun setFirstTime(value: Boolean, context: Context): Boolean {
        settings = provideSettings(context)
        settings.putBoolean(FIRST_TIME, value)
        return settings.get<Boolean>(FIRST_TIME) ?: false
    }

    fun getFirstTime(context: Context): Boolean? {
        settings = provideSettings(context)
        return settings.get<Boolean>(FIRST_TIME)
    }

    fun setToken(value: String, context: Context): String {
        settings = provideSettings(context)
        settings.putString(ACCESS_TOKEN, value)
        return settings.get<String>(ACCESS_TOKEN) ?: ""
    }

    fun getToken(context: Context): String {
        settings = provideSettings(context)
        return settings.get<String>(ACCESS_TOKEN) ?: ""
    }

    fun setRefreshToken(value: String, context: Context): String {
        settings = provideSettings(context)
        settings.putString(REFRESH_TOKEN, value)
        return settings.get<String>(REFRESH_TOKEN) ?: ""
    }

    fun getRefreshToken(context: Context): String {
        settings = provideSettings(context)
        return settings.get<String>(REFRESH_TOKEN) ?: ""
    }

    fun clearToken(context: Context) :Boolean {
        settings = provideSettings(context)
        settings.remove(ACCESS_TOKEN)
        settings.remove(REFRESH_TOKEN)
        return true
    }
}