package com.luckyfrog.quickmart.core.di

import com.luckyfrog.quickmart.features.auth.data.repositories.AuthRepositoryImpl
import com.luckyfrog.quickmart.features.auth.domain.repositories.AuthRepository
import com.luckyfrog.quickmart.features.product.data.repositories.ProductRepositoryImpl
import com.luckyfrog.quickmart.features.product.domain.repositories.ProductRepository
import com.luckyfrog.quickmart.features.profile.data.repositories.ProfileRepositoryImpl
import com.luckyfrog.quickmart.features.profile.domain.repositories.ProfileRepository
import org.koin.dsl.module

val repositoryModule = module {
    factory<AuthRepository> { AuthRepositoryImpl(remoteDataSource = get()) }

    factory<ProductRepository> { ProductRepositoryImpl(remoteDataSource = get()) }
    
    factory<ProfileRepository> { ProfileRepositoryImpl(remoteDataSource = get()) }
}
