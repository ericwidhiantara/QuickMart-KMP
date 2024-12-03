package com.luckyfrog.quickmart.core.di

import com.luckyfrog.quickmart.features.auth.domain.repositories.AuthRepository
import com.luckyfrog.quickmart.features.auth.data.repositories.AuthRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {
    factory<AuthRepository> { AuthRepositoryImpl(remoteDataSource = get()) }
}
