package com.luckyfrog.quickmart.core.di

import com.luckyfrog.quickmart.features.auth.data.datasources.remote.AuthRemoteDataSource
import com.luckyfrog.quickmart.features.auth.data.datasources.remote.AuthRemoteDataSourceImpl
import org.koin.dsl.module

val dataSourceModule = module {
    factory<AuthRemoteDataSource> {
        AuthRemoteDataSourceImpl(
            api = get(),  // Assuming you have HttpClient provided elsewhere
            settings = get()
        )
    }
}
