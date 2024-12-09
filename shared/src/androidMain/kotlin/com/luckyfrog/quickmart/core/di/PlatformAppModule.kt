package com.luckyfrog.quickmart.core.di

import android.content.Context
import androidx.preference.PreferenceManager
import com.russhwolf.settings.Settings
import com.russhwolf.settings.SharedPreferencesSettings
import org.koin.core.module.Module
import org.koin.dsl.module

actual fun platformAppModule(): Module = module {
    single { provideSettings(get<Context>()) }
    single { DatabaseDriverFactory(get()) }

}

fun provideSettings(context: Context): Settings {
    val preferences = PreferenceManager.getDefaultSharedPreferences(context)
    return SharedPreferencesSettings(preferences)
}
