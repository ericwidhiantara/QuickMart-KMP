package com.luckyfrog.quickmart.core.di

import com.luckyfrog.quickmart.features.auth.data.repositories.AuthRepositoryImpl
import com.luckyfrog.quickmart.features.auth.domain.repositories.AuthRepository
import com.luckyfrog.quickmart.features.cart.data.repositories.CartLocalRepositoryImpl
import com.luckyfrog.quickmart.features.cart.domain.repositories.CartLocalRepository
import com.luckyfrog.quickmart.features.category.data.repositories.CategoryRepositoryImpl
import com.luckyfrog.quickmart.features.category.domain.repositories.CategoryRepository
import com.luckyfrog.quickmart.features.order.data.repositories.OrderRepositoryImpl
import com.luckyfrog.quickmart.features.order.domain.repositories.OrderRepository
import com.luckyfrog.quickmart.features.review.data.repositories.ReviewRepositoryImpl
import com.luckyfrog.quickmart.features.review.domain.repositories.ReviewRepository
import com.luckyfrog.quickmart.features.shipping_address.data.repositories.ShippingAddressRepositoryImpl
import com.luckyfrog.quickmart.features.shipping_address.domain.repositories.ShippingAddressRepository
import com.luckyfrog.quickmart.features.product.data.repositories.ProductRepositoryImpl
import com.luckyfrog.quickmart.features.product.domain.repositories.ProductRepository
import com.luckyfrog.quickmart.features.profile.data.repositories.ProfileRepositoryImpl
import com.luckyfrog.quickmart.features.profile.domain.repositories.ProfileRepository
import com.luckyfrog.quickmart.features.wallet.data.repositories.WalletRepositoryImpl
import com.luckyfrog.quickmart.features.wallet.domain.repositories.WalletRepository
import com.luckyfrog.quickmart.features.wishlist.data.repositories.WishlistLocalRepositoryImpl
import com.luckyfrog.quickmart.features.wishlist.domain.repositories.WishlistLocalRepository
import org.koin.dsl.module

val repositoryModule = module {
    factory<AuthRepository> { AuthRepositoryImpl(remoteDataSource = get()) }

    factory<ProductRepository> { ProductRepositoryImpl(remoteDataSource = get()) }

    factory<ProfileRepository> { ProfileRepositoryImpl(remoteDataSource = get()) }

    factory<CategoryRepository> { CategoryRepositoryImpl(remoteDataSource = get()) }

    factory<OrderRepository> { OrderRepositoryImpl(remoteDataSource = get()) }

    factory<ReviewRepository> { ReviewRepositoryImpl(remoteDataSource = get()) }

    factory<ShippingAddressRepository> { ShippingAddressRepositoryImpl(remoteDataSource = get()) }

    factory<WalletRepository> { WalletRepositoryImpl(remoteDataSource = get()) }

    factory<CartLocalRepository> { CartLocalRepositoryImpl(localDataSource = get()) }

    factory<WishlistLocalRepository> { WishlistLocalRepositoryImpl(localDataSource = get()) }
}
