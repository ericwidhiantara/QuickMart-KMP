package com.luckyfrog.quickmart.core.di

import com.luckyfrog.quickmart.core.app.MainViewModel
import com.luckyfrog.quickmart.features.auth.presentation.login.LoginViewModel
import com.luckyfrog.quickmart.features.auth.presentation.register.RegisterViewModel
import org.koin.core.module.Module
import org.koin.dsl.module

actual fun platformViewModelModule(): Module = module {
    factory { LoginViewModel(get()) }
    factory { RegisterViewModel(get()) }
    factory { MainViewModel(
        application = get()
    ) }

    // Add other Android-specific view models here
}