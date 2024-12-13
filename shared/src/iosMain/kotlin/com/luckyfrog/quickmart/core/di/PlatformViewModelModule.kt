package com.luckyfrog.quickmart.core.di

import com.luckyfrog.quickmart.features.auth.presentation.login.LoginViewModel
import com.russhwolf.settings.NSUserDefaultsSettings
import com.russhwolf.settings.Settings
import org.koin.dsl.module
import platform.Foundation.NSUserDefaults

actual fun platformViewModelModule() = module {
    single {
        LoginViewModel(get())
    }

    single<Settings> { provideSettings() }
}

fun provideSettings(): Settings {
    return NSUserDefaultsSettings(NSUserDefaults.standardUserDefaults)
}