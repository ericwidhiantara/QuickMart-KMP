package com.luckyfrog.quickmart.core.di

import com.luckyfrog.quickmart.features.auth.domain.repositories.AuthRepository
import com.luckyfrog.quickmart.features.auth.data.repositories.AuthRepositoryImpl
import com.luckyfrog.quickmart.features.product.data.repositories.ProductRepositoryImpl
import com.luckyfrog.quickmart.features.product.domain.repositories.ProductRepository
import org.koin.dsl.module

val repositoryModule = module {
    factory<AuthRepository> { AuthRepositoryImpl(remoteDataSource = get()) }
    factory<ProductRepository> { ProductRepositoryImpl(remoteDataSource = get()) }
}
