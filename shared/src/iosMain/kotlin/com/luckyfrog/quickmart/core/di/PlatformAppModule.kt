package com.luckyfrog.quickmart.core.di

import com.russhwolf.settings.NSUserDefaultsSettings
import com.russhwolf.settings.Settings
import org.koin.core.module.Module
import org.koin.dsl.module
import platform.Foundation.NSUserDefaults

actual fun platformAppModule(): Module = module {
    single { CartDatabaseDriverFactory() }
    single { WishlistDatabaseDriverFactory() }
    single<Settings> { provideSettings() }
}


fun provideSettings(): Settings {
    return NSUserDefaultsSettings(NSUserDefaults.standardUserDefaults)
}