package com.luckyfrog.quickmart.core.di

import com.luckyfrog.quickmart.features.auth.data.datasources.remote.AuthApi
import com.luckyfrog.quickmart.features.auth.data.datasources.remote.AuthApiImpl
import com.luckyfrog.quickmart.features.product.data.datasources.remote.ProductApi
import com.luckyfrog.quickmart.features.product.data.datasources.remote.ProductApiImpl
import org.koin.dsl.module


val apiModule = module {
    factory<AuthApi> {
        AuthApiImpl(
            get()
        )
    }
    factory<ProductApi> {
        ProductApiImpl(
            get()
        )
    }
}