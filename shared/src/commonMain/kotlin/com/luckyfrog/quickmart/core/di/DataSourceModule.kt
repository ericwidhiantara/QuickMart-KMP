package com.luckyfrog.quickmart.core.di

import com.luckyfrog.quickmart.features.auth.data.datasources.remote.AuthRemoteDataSource
import com.luckyfrog.quickmart.features.auth.data.datasources.remote.AuthRemoteDataSourceImpl
import com.luckyfrog.quickmart.features.product.data.datasources.remote.ProductRemoteDataSource
import com.luckyfrog.quickmart.features.product.data.datasources.remote.ProductRemoteDataSourceImpl
import org.koin.dsl.module

val dataSourceModule = module {
    factory<AuthRemoteDataSource> {
        AuthRemoteDataSourceImpl(
            api = get(),
            settings = get()
        )
    }

    factory<ProductRemoteDataSource> {
        ProductRemoteDataSourceImpl(
            api = get(),
        )
    }

}
