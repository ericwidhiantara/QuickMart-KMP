package com.luckyfrog.quickmart.core.di

import com.luckyfrog.quickmart.features.auth.data.datasources.remote.AuthApi
import com.luckyfrog.quickmart.features.auth.data.datasources.remote.AuthRemoteDataSource
import com.luckyfrog.quickmart.features.auth.data.datasources.remote.AuthRemoteDataSourceImpl
import com.luckyfrog.quickmart.features.auth.data.repositories.AuthRepositoryImpl
import com.luckyfrog.quickmart.features.auth.domain.repositories.AuthRepository
import com.luckyfrog.quickmart.features.auth.domain.usecases.GetUserUseCase
import com.luckyfrog.quickmart.features.auth.domain.usecases.LoginUseCase
import com.luckyfrog.quickmart.features.product.data.datasources.remote.ProductApi
import com.luckyfrog.quickmart.features.product.data.datasources.remote.ProductRemoteDataSource
import com.luckyfrog.quickmart.features.product.data.datasources.remote.ProductRemoteDataSourceImpl
import com.luckyfrog.quickmart.features.product.data.repositories.ProductRepositoryImpl
import com.luckyfrog.quickmart.features.product.domain.repositories.ProductRepository
import com.luckyfrog.quickmart.features.product.domain.usecases.GetProductDetailUseCase
import com.luckyfrog.quickmart.features.product.domain.usecases.GetProductsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    /// Data Sources
    @Singleton
    @Provides
    fun providesAuthRemoteDataSource(
        api: AuthApi
    ): AuthRemoteDataSource {
        return AuthRemoteDataSourceImpl(api)
    }

    @Singleton
    @Provides
    fun providesProductRemoteDataSource(
        api: ProductApi
    ): ProductRemoteDataSource {
        return ProductRemoteDataSourceImpl(api)
    }

    @Module
    @InstallIn(SingletonComponent::class)
    object RepositoryModule {

        /// Repositories
        @Singleton
        @Provides
        fun providesAuthRepository(
            remoteDataSource: AuthRemoteDataSource
        ): AuthRepository {
            return AuthRepositoryImpl(remoteDataSource)
        }

        @Singleton
        @Provides
        fun providesProductRepository(
            remoteDataSource: ProductRemoteDataSource
        ): ProductRepository {
            return ProductRepositoryImpl(remoteDataSource)
        }


    }

    @Module
    @InstallIn(SingletonComponent::class)
    object UseCaseModule {

        /// Auth
        @Singleton
        @Provides
        fun providesLoginUseCase(
            repository: AuthRepository
        ): LoginUseCase {
            return LoginUseCase(repository)
        }

        @Singleton
        @Provides
        fun providesGetUserUseCase(
            repository: AuthRepository
        ): GetUserUseCase {
            return GetUserUseCase(repository)
        }

        /// Products
        @Singleton
        @Provides
        fun providesGetProductsUseCase(
            repository: ProductRepository
        ): GetProductsUseCase {
            return GetProductsUseCase(repository)
        }

        @Singleton
        @Provides
        fun providesGetProductDetailUseCase(
            repository: ProductRepository
        ): GetProductDetailUseCase {
            return GetProductDetailUseCase(repository)
        }

    }
}