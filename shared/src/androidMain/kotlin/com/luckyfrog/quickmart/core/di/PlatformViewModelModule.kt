package com.luckyfrog.quickmart.core.di

import com.luckyfrog.quickmart.core.app.MainViewModel
import com.luckyfrog.quickmart.presentation.login.LoginViewModel
import org.koin.core.module.Module
import org.koin.dsl.module

actual fun platformViewModelModule(): Module = module {
    factory { LoginViewModel(get()) }
    factory { MainViewModel() }

    // Add other Android-specific view models here
}