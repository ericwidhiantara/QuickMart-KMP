package com.luckyfrog.quickmart.core.di

import com.luckyfrog.quickmart.features.auth.domain.usecases.ForgotPasswordChangePasswordUseCase
import com.luckyfrog.quickmart.features.auth.domain.usecases.ForgotPasswordSendOTPUseCase
import com.luckyfrog.quickmart.features.auth.domain.usecases.ForgotPasswordVerifyOTPUseCase
import com.luckyfrog.quickmart.features.auth.domain.usecases.LoginUseCase
import com.luckyfrog.quickmart.features.auth.domain.usecases.RegisterUseCase
import com.luckyfrog.quickmart.features.cart.domain.usecases.local.CalculateSubtotalUseCase
import com.luckyfrog.quickmart.features.cart.domain.usecases.local.DeleteCartItemUseCase
import com.luckyfrog.quickmart.features.cart.domain.usecases.local.GetCartItemsUseCase
import com.luckyfrog.quickmart.features.cart.domain.usecases.local.GetSelectedCartItemsUseCase
import com.luckyfrog.quickmart.features.cart.domain.usecases.local.InsertCartItemUseCase
import com.luckyfrog.quickmart.features.cart.domain.usecases.local.UpdateCartItemUseCase
import com.luckyfrog.quickmart.features.category.domain.usecases.GetCategoriesUseCase
import com.luckyfrog.quickmart.features.order.domain.usecases.CancelOrderUseCase
import com.luckyfrog.quickmart.features.order.domain.usecases.CheckoutUseCase
import com.luckyfrog.quickmart.features.order.domain.usecases.GetOrderDetailUseCase
import com.luckyfrog.quickmart.features.order.domain.usecases.GetOrdersUseCase
import com.luckyfrog.quickmart.features.product.domain.usecases.GetProductDetailUseCase
import com.luckyfrog.quickmart.features.product.domain.usecases.GetProductsUseCase
import com.luckyfrog.quickmart.features.profile.domain.usecases.ChangePasswordUseCase
import com.luckyfrog.quickmart.features.profile.domain.usecases.CheckPasswordUseCase
import com.luckyfrog.quickmart.features.profile.domain.usecases.DeleteAccountUseCase
import com.luckyfrog.quickmart.features.profile.domain.usecases.GetUserUseCase
import com.luckyfrog.quickmart.features.profile.domain.usecases.SendOTPUseCase
import com.luckyfrog.quickmart.features.profile.domain.usecases.UpdateProfileUseCase
import com.luckyfrog.quickmart.features.profile.domain.usecases.VerifyOTPUseCase
import com.luckyfrog.quickmart.features.wallet.domain.usecases.GetWalletUseCase
import com.luckyfrog.quickmart.features.wallet.domain.usecases.TopUpWalletUseCase
import com.luckyfrog.quickmart.features.review.domain.usecases.CreateReviewUseCase
import com.luckyfrog.quickmart.features.review.domain.usecases.DeleteReviewUseCase
import com.luckyfrog.quickmart.features.review.domain.usecases.GetProductReviewsUseCase
import com.luckyfrog.quickmart.features.shipping_address.domain.usecases.CreateShippingAddressUseCase
import com.luckyfrog.quickmart.features.shipping_address.domain.usecases.DeleteShippingAddressUseCase
import com.luckyfrog.quickmart.features.shipping_address.domain.usecases.GetShippingAddressesUseCase
import com.luckyfrog.quickmart.features.shipping_address.domain.usecases.SetDefaultShippingAddressUseCase
import com.luckyfrog.quickmart.features.shipping_address.domain.usecases.UpdateShippingAddressUseCase
import com.luckyfrog.quickmart.features.wishlist.domain.usecases.local.DeleteWishlistItemUseCase
import com.luckyfrog.quickmart.features.wishlist.domain.usecases.local.GetWishlistItemsUseCase
import com.luckyfrog.quickmart.features.wishlist.domain.usecases.local.InsertWishlistItemUseCase
import org.koin.dsl.module

val useCaseModule = module {
    // AUTH
    factory { ForgotPasswordChangePasswordUseCase(repository = get()) }
    factory { ForgotPasswordSendOTPUseCase(repository = get()) }
    factory { ForgotPasswordVerifyOTPUseCase(repository = get()) }
    factory { LoginUseCase(repository = get()) }
    factory { RegisterUseCase(repository = get()) }

    // PRODUCT
    factory { GetProductsUseCase(repository = get()) }
    factory { GetProductDetailUseCase(repository = get()) }

    // PROFILE
    factory { GetUserUseCase(repository = get()) }
    factory { CheckPasswordUseCase(repository = get()) }
    factory { ChangePasswordUseCase(repository = get()) }
    factory { SendOTPUseCase(repository = get()) }
    factory { VerifyOTPUseCase(repository = get()) }
    factory { UpdateProfileUseCase(repository = get()) }
    factory { DeleteAccountUseCase(repository = get()) }

    // WALLET
    factory { GetWalletUseCase(repository = get()) }
    factory { TopUpWalletUseCase(repository = get()) }

    // CATEGORY
    factory { GetCategoriesUseCase(repository = get()) }

    // ORDER
    factory { CheckoutUseCase(repository = get()) }
    factory { GetOrdersUseCase(repository = get()) }
    factory { GetOrderDetailUseCase(repository = get()) }
    factory { CancelOrderUseCase(repository = get()) }

    // REVIEW
    factory { GetProductReviewsUseCase(repository = get()) }
    factory { CreateReviewUseCase(repository = get()) }
    factory { DeleteReviewUseCase(repository = get()) }

    // SHIPPING ADDRESS
    factory { GetShippingAddressesUseCase(repository = get()) }
    factory { CreateShippingAddressUseCase(repository = get()) }
    factory { UpdateShippingAddressUseCase(repository = get()) }
    factory { DeleteShippingAddressUseCase(repository = get()) }
    factory { SetDefaultShippingAddressUseCase(repository = get()) }

    // CART
    factory { CalculateSubtotalUseCase(repository = get()) }
    factory { DeleteCartItemUseCase(repository = get()) }
    factory { GetCartItemsUseCase(repository = get()) }
    factory { GetSelectedCartItemsUseCase(repository = get()) }
    factory { InsertCartItemUseCase(repository = get()) }
    factory { UpdateCartItemUseCase(repository = get()) }

    // WISHLIST
    factory { DeleteWishlistItemUseCase(repository = get()) }
    factory { GetWishlistItemsUseCase(repository = get()) }
    factory { InsertWishlistItemUseCase(repository = get()) }

}
