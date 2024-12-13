package com.luckyfrog.quickmart.core.di

import com.luckyfrog.quickmart.features.auth.data.datasources.remote.AuthApi
import com.luckyfrog.quickmart.features.auth.data.datasources.remote.AuthApiImpl
import com.luckyfrog.quickmart.features.category.data.datasources.remote.CategoryApi
import com.luckyfrog.quickmart.features.category.data.datasources.remote.CategoryApiImpl
import com.luckyfrog.quickmart.features.product.data.datasources.remote.ProductApi
import com.luckyfrog.quickmart.features.product.data.datasources.remote.ProductApiImpl
import com.luckyfrog.quickmart.features.profile.data.datasources.remote.ProfileApi
import com.luckyfrog.quickmart.features.profile.data.datasources.remote.ProfileApiImpl
import io.ktor.client.HttpClient
import org.koin.core.qualifier.named
import org.koin.dsl.module


val apiModule = module {
    factory<AuthApi> {
        AuthApiImpl(
            get<HttpClient>(named("AuthHttpClient"))
        )
    }

    factory<ProductApi> {
        ProductApiImpl(
            get()
        )
    }

    factory<ProfileApi> {
        ProfileApiImpl(
            get()
        )
    }

    factory<CategoryApi> {
        CategoryApiImpl(
            get()
        )
    }
}