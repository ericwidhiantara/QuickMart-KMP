package com.luckyfrog.quickmart.utils

import com.luckyfrog.quickmart.core.di.getSharedModules
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
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.context.startKoin

fun initKoin() {
    startKoin {
        modules(getSharedModules())
    }
}

class KoinHelper : KoinComponent {
    // Authentication
    fun getLoginViewModel() = get<LoginViewModel>()
    fun getRegisterViewModel() = get<RegisterViewModel>()
    fun getEmailVerificationViewModel() = get<EmailVerificationViewModel>()
    fun getCreatePasswordViewModel() = get<CreatePasswordViewModel>()
    fun getForgotPasswordEmailConfirmationViewModel() =
        get<ForgotPasswordEmailConfirmationViewModel>()

    fun getForgotPasswordVerifyCodeViewModel() = get<ForgotPasswordVerifyCodeViewModel>()

    // General
    fun getNavBarViewModel() = get<NavBarViewModel>()

    // Profile
    fun getChangePasswordViewModel() = get<ChangePasswordViewModel>()
    fun getCheckPasswordViewModel() = get<CheckPasswordViewModel>()
    fun getUserViewModel() = get<UserViewModel>()

    // Category
    fun getCategoryListViewModel() = get<CategoryListViewModel>()

    // Cart
    fun getMyCartViewModel() = get<MyCartViewModel>()

    // Wishlist
    fun getMyWishlistViewModel() = get<MyWishlistViewModel>()

    // Product
    fun getProductDetailViewModel() = get<ProductDetailViewModel>()
    fun getProductListViewModel() = get<ProductListViewModel>()

}