package com.luckyfrog.quickmart.core.di

import com.luckyfrog.quickmart.features.auth.presentation.email_verification.EmailVerificationViewModel
import com.luckyfrog.quickmart.features.auth.presentation.forgot_password.create_password.CreatePasswordViewModel
import com.luckyfrog.quickmart.features.auth.presentation.forgot_password.email_confirmation.ForgotPasswordEmailConfirmationViewModel
import com.luckyfrog.quickmart.features.auth.presentation.forgot_password.verify_code.ForgotPasswordVerifyCodeViewModel
import com.luckyfrog.quickmart.features.auth.presentation.login.LoginViewModel
import com.luckyfrog.quickmart.features.auth.presentation.register.RegisterViewModel
import com.luckyfrog.quickmart.features.cart.presentation.my_cart.MyCartViewModel
import com.luckyfrog.quickmart.features.category.presentation.categories.CategoryListViewModel
import com.luckyfrog.quickmart.features.general.presentation.NavBarViewModel
import com.luckyfrog.quickmart.features.product.presentation.product_detail.ProductDetailViewModel
import com.luckyfrog.quickmart.features.product.presentation.product_list.ProductListViewModel
import com.luckyfrog.quickmart.features.profile.presentation.change_password.ChangePasswordViewModel
import com.luckyfrog.quickmart.features.profile.presentation.check_password.CheckPasswordViewModel
import com.luckyfrog.quickmart.features.profile.presentation.profile.UserViewModel
import com.luckyfrog.quickmart.features.wishlist.presentation.my_wishlist.MyWishlistViewModel
import org.koin.dsl.module

val viewModelModule = module {
    // Auth
    factory { LoginViewModel(get()) }
    factory { RegisterViewModel(get()) }
    factory { EmailVerificationViewModel(get(), get()) }
    factory { CreatePasswordViewModel(get()) }
    factory { ForgotPasswordEmailConfirmationViewModel(get()) }
    factory { ForgotPasswordVerifyCodeViewModel(get()) }
    
    // General
    factory { NavBarViewModel() }

    // Profile
    factory { ChangePasswordViewModel(get()) }
    factory { CheckPasswordViewModel(get()) }
    factory { UserViewModel(get()) }

    // Category
    factory { CategoryListViewModel(get()) }

    // Cart
    factory { MyCartViewModel(get(), get(), get(), get(), get(), get()) }

    // Wishlist
    factory { MyWishlistViewModel(get(), get(), get()) }

    // Product
    factory { ProductDetailViewModel(get()) }
    factory { ProductListViewModel(get()) }
}