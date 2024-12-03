package com.luckyfrog.quickmart.core.di

import android.content.SharedPreferences
import android.preference.PreferenceManager
import androidx.compose.ui.platform.LocalContext
import com.russhwolf.settings.Settings
import com.russhwolf.settings.SharedPreferencesSettings
import org.koin.dsl.module


fun appModule(sharedPref: SharedPreferences) = module {
    single<Settings> {
        SharedPreferencesSettings(sharedPref)
    }
}