package com.luckyfrog.quickmart.core.di

import com.luckyfrog.quickmart.core.app.MainViewModel
import com.luckyfrog.quickmart.features.auth.presentation.email_verification.EmailVerificationViewModel
import com.luckyfrog.quickmart.features.auth.presentation.forgot_password.create_password.CreatePasswordViewModel
import com.luckyfrog.quickmart.features.auth.presentation.forgot_password.email_confirmation.ForgotPasswordEmailConfirmationViewModel
import com.luckyfrog.quickmart.features.auth.presentation.forgot_password.verify_code.ForgotPasswordVerifyCodeViewModel
import com.luckyfrog.quickmart.features.auth.presentation.login.LoginViewModel
import com.luckyfrog.quickmart.features.auth.presentation.register.RegisterViewModel
import com.luckyfrog.quickmart.features.general.presentation.main.NavBarViewModel
import org.koin.core.module.Module
import org.koin.dsl.module

actual fun platformViewModelModule(): Module = module {
    // Auth
    factory { LoginViewModel(get()) }
    factory { RegisterViewModel(get()) }
    factory { EmailVerificationViewModel(get(), get()) }
    factory { CreatePasswordViewModel(get()) }
    factory { ForgotPasswordEmailConfirmationViewModel(get()) }
    factory { ForgotPasswordVerifyCodeViewModel(get()) }

    // General
    factory { MainViewModel(
        application = get()
    ) }
    factory { NavBarViewModel() }

}