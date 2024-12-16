package com.luckyfrog.quickmart.core.di

import com.luckyfrog.quickmart.features.auth.presentation.email_verification.EmailVerificationViewModel
import com.luckyfrog.quickmart.features.auth.presentation.forgot_password.create_password.CreatePasswordViewModel
import com.luckyfrog.quickmart.features.auth.presentation.forgot_password.email_confirmation.ForgotPasswordEmailConfirmationViewModel
import com.luckyfrog.quickmart.features.auth.presentation.forgot_password.verify_code.ForgotPasswordVerifyCodeViewModel
import com.luckyfrog.quickmart.features.auth.presentation.login.LoginViewModel
import com.luckyfrog.quickmart.features.auth.presentation.register.RegisterViewModel
import org.koin.dsl.module

actual fun platformViewModelModule() = module {
    single { LoginViewModel(get()) }
    single { RegisterViewModel(get()) }
    single { EmailVerificationViewModel(get(), get()) }
    single { CreatePasswordViewModel(get()) }
    single { ForgotPasswordEmailConfirmationViewModel(get()) }
    single { ForgotPasswordVerifyCodeViewModel(get()) }


}
