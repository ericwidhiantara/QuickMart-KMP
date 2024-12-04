package com.luckyfrog.quickmart.core.di

import android.content.Context
import android.preference.PreferenceManager
import androidx.compose.ui.platform.LocalContext
import com.luckyfrog.quickmart.core.app.MainViewModel
import com.luckyfrog.quickmart.presentation.login.LoginViewModel
import com.russhwolf.settings.Settings
import com.russhwolf.settings.SharedPreferencesSettings
import org.koin.core.module.Module
import org.koin.dsl.module

actual fun platformAppModule(): Module = module {
    single { provideSettings(get()) }
}

fun provideSettings(context: Context): Settings {
    val preferences = PreferenceManager.getDefaultSharedPreferences(context)
    return SharedPreferencesSettings(preferences)
}