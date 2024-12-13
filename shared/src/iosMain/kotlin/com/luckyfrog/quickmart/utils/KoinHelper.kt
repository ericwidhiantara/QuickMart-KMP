package com.luckyfrog.quickmart.utils

import com.luckyfrog.quickmart.core.di.getSharedModules
import com.luckyfrog.quickmart.features.auth.presentation.login.LoginViewModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.context.startKoin

fun initKoin() {
    startKoin {
        modules(getSharedModules())
    }
}

class KoinHelper : KoinComponent {
    fun getLoginViewModel() = get<LoginViewModel>()
}