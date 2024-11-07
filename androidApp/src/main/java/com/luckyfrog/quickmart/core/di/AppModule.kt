package com.luckyfrog.quickmart.core.di

import com.luckyfrog.quickmart.features.auth.data.datasources.remote.AuthApi
import com.luckyfrog.quickmart.features.auth.data.datasources.remote.AuthRemoteDataSource
import com.luckyfrog.quickmart.features.auth.data.datasources.remote.AuthRemoteDataSourceImpl
import com.luckyfrog.quickmart.features.auth.data.repositories.AuthRepositoryImpl
import com.luckyfrog.quickmart.features.auth.domain.repositories.AuthRepository
import com.luckyfrog.quickmart.features.auth.domain.usecases.GetUserUseCase
import com.luckyfrog.quickmart.features.auth.domain.usecases.LoginUseCase
import com.luckyfrog.quickmart.features.auth.domain.usecases.RegisterUseCase
import com.luckyfrog.quickmart.features.auth.domain.usecases.SendOTPUseCase
import com.luckyfrog.quickmart.features.category.data.datasources.remote.CategoryApi
import com.luckyfrog.quickmart.features.category.data.datasources.remote.CategoryRemoteDataSource
import com.luckyfrog.quickmart.features.category.data.repositories.CategoryRepositoryImpl
import com.luckyfrog.quickmart.features.category.domain.repositories.CategoryRepository
import com.luckyfrog.quickmart.features.category.domain.usecases.GetCategoriesUseCase
import com.luckyfrog.quickmart.features.product.data.datasources.remote.CategoryRemoteDataSourceImpl
import com.luckyfrog.quickmart.features.product.data.datasources.remote.ProductApi
import com.luckyfrog.quickmart.features.product.data.datasources.remote.ProductRemoteDataSource
import com.luckyfrog.quickmart.features.product.data.datasources.remote.ProductRemoteDataSourceImpl
import com.luckyfrog.quickmart.features.product.data.repositories.ProductRepositoryImpl
import com.luckyfrog.quickmart.features.product.domain.repositories.ProductRepository
import com.luckyfrog.quickmart.features.product.domain.usecases.GetProductDetailUseCase
import com.luckyfrog.quickmart.features.product.domain.usecases.GetProductsByCategoryUseCase
import com.luckyfrog.quickmart.features.product.domain.usecases.GetProductsUseCase
import com.luckyfrog.quickmart.utils.TokenManager
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
        api: AuthApi,
        tokenManager: TokenManager
    ): AuthRemoteDataSource {
        return AuthRemoteDataSourceImpl(api, tokenManager)
    }

    @Singleton
    @Provides
    fun providesProductRemoteDataSource(
        api: ProductApi
    ): ProductRemoteDataSource {
        return ProductRemoteDataSourceImpl(api)
    }

    @Singleton
    @Provides
    fun providesCategoryRemoteDataSource(
        api: CategoryApi
    ): CategoryRemoteDataSource {
        return CategoryRemoteDataSourceImpl(api)
    }


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

    @Singleton
    @Provides
    fun providesCategoryRepository(
        remoteDataSource: CategoryRemoteDataSource
    ): CategoryRepository {
        return CategoryRepositoryImpl(remoteDataSource)
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
    fun providesRegisterUseCase(
        repository: AuthRepository
    ): RegisterUseCase {
        return RegisterUseCase(repository)
    }

    @Singleton
    @Provides
    fun providesSendOTPUseCase(
        repository: AuthRepository
    ): SendOTPUseCase {
        return SendOTPUseCase(repository)
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
    fun providesGetProductsByCategoryUseCase(
        repository: ProductRepository
    ): GetProductsByCategoryUseCase {
        return GetProductsByCategoryUseCase(repository)
    }


    @Singleton
    @Provides
    fun providesGetProductDetailUseCase(
        repository: ProductRepository
    ): GetProductDetailUseCase {
        return GetProductDetailUseCase(repository)
    }

    /// Category
    @Singleton
    @Provides
    fun providesGetCategoriesUseCase(
        repository: CategoryRepository
    ): GetCategoriesUseCase {
        return GetCategoriesUseCase(repository)
    }

}
