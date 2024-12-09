package com.luckyfrog.quickmart.core.di

import com.luckyfrog.quickmart.features.auth.data.datasources.remote.AuthRemoteDataSource
import com.luckyfrog.quickmart.features.auth.data.datasources.remote.AuthRemoteDataSourceImpl
import com.luckyfrog.quickmart.features.cart.data.datasources.local.CartLocalDataSource
import com.luckyfrog.quickmart.features.cart.data.datasources.local.CartLocalDataSourceImpl
import com.luckyfrog.quickmart.features.category.data.datasources.remote.CategoryRemoteDataSource
import com.luckyfrog.quickmart.features.category.data.datasources.remote.CategoryRemoteDataSourceImpl
import com.luckyfrog.quickmart.features.product.data.datasources.remote.ProductRemoteDataSource
import com.luckyfrog.quickmart.features.product.data.datasources.remote.ProductRemoteDataSourceImpl
import com.luckyfrog.quickmart.features.profile.data.datasources.remote.ProfileRemoteDataSource
import com.luckyfrog.quickmart.features.profile.data.datasources.remote.ProfileRemoteDataSourceImpl
import com.luckyfrog.quickmart.features.wishlist.data.datasources.local.WishlistLocalDataSource
import com.luckyfrog.quickmart.features.wishlist.data.datasources.local.WishlistLocalDataSourceImpl
import org.koin.dsl.module

val dataSourceModule = module {
    factory<AuthRemoteDataSource> {
        AuthRemoteDataSourceImpl(
            api = get()
        )
    }

    factory<ProductRemoteDataSource> {
        ProductRemoteDataSourceImpl(
            api = get(),
        )
    }

    factory<ProfileRemoteDataSource> {
        ProfileRemoteDataSourceImpl(
            api = get(),
        )
    }

    factory<CategoryRemoteDataSource> {
        CategoryRemoteDataSourceImpl(
            api = get(),
        )
    }

    factory<CartLocalDataSource> {
        CartLocalDataSourceImpl(
            database = get()
        )
    }

    factory<WishlistLocalDataSource> {
        WishlistLocalDataSourceImpl(
            database = get()
        )
    }

}
