package com.luckyfrog.quickmart.core.di

import com.luckyfrog.quickmart.features.auth.presentation.login.LoginViewModel
import org.koin.dsl.module

val viewModelModule = module {
    factory { LoginViewModel(get()) }
}
