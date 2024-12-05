package com.luckyfrog.quickmart.core.di

import com.luckyfrog.quickmart.features.auth.domain.usecases.ForgotPasswordChangePasswordUseCase
import com.luckyfrog.quickmart.features.auth.domain.usecases.ForgotPasswordSendOTPUseCase
import com.luckyfrog.quickmart.features.auth.domain.usecases.ForgotPasswordVerifyOTPUseCase
import com.luckyfrog.quickmart.features.auth.domain.usecases.GetUserUseCase
import com.luckyfrog.quickmart.features.auth.domain.usecases.LoginUseCase
import com.luckyfrog.quickmart.features.auth.domain.usecases.RegisterUseCase
import com.luckyfrog.quickmart.features.auth.domain.usecases.SendOTPUseCase
import com.luckyfrog.quickmart.features.auth.domain.usecases.VerifyOTPUseCase
import com.luckyfrog.quickmart.features.product.domain.usecases.GetProductDetailUseCase
import com.luckyfrog.quickmart.features.product.domain.usecases.GetProductsUseCase
import com.luckyfrog.quickmart.features.profile.domain.usecases.ChangePasswordUseCase
import com.luckyfrog.quickmart.features.profile.domain.usecases.CheckPasswordUseCase
import org.koin.dsl.module

val useCaseModule = module {
    // AUTH
    factory { ForgotPasswordChangePasswordUseCase(repository = get()) }
    factory { ForgotPasswordSendOTPUseCase(repository = get()) }
    factory { ForgotPasswordVerifyOTPUseCase(repository = get()) }
    factory { GetUserUseCase(repository = get()) }
    factory { LoginUseCase(repository = get()) }
    factory { RegisterUseCase(repository = get()) }
    factory { SendOTPUseCase(repository = get()) }
    factory { VerifyOTPUseCase(repository = get()) }

    // PRODUCT
    factory { GetProductsUseCase(repository = get()) }
    factory { GetProductDetailUseCase(repository = get()) }

    // PROFILE
    factory { CheckPasswordUseCase(repository = get()) }
    factory { ChangePasswordUseCase(repository = get()) }

}
