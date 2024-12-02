package com.luckyfrog.quickmart.core.di

import android.content.Context
import androidx.room.Room
import com.luckyfrog.quickmart.features.auth.data.datasources.remote.AuthApi
import com.luckyfrog.quickmart.features.auth.data.datasources.remote.AuthRemoteDataSource
import com.luckyfrog.quickmart.features.auth.data.datasources.remote.AuthRemoteDataSourceImpl
import com.luckyfrog.quickmart.features.auth.data.repositories.AuthRepositoryImpl
import com.luckyfrog.quickmart.features.auth.domain.repositories.AuthRepository
import com.luckyfrog.quickmart.features.auth.domain.usecases.ForgotPasswordChangePasswordUseCase
import com.luckyfrog.quickmart.features.auth.domain.usecases.ForgotPasswordSendOTPUseCase
import com.luckyfrog.quickmart.features.auth.domain.usecases.ForgotPasswordVerifyOTPUseCase
import com.luckyfrog.quickmart.features.auth.domain.usecases.GetUserUseCase
import com.luckyfrog.quickmart.features.auth.domain.usecases.LoginUseCase
import com.luckyfrog.quickmart.features.auth.domain.usecases.RegisterUseCase
import com.luckyfrog.quickmart.features.auth.domain.usecases.SendOTPUseCase
import com.luckyfrog.quickmart.features.auth.domain.usecases.VerifyOTPUseCase
import com.luckyfrog.quickmart.features.cart.data.datasources.local.CartAppDatabase
import com.luckyfrog.quickmart.features.cart.data.datasources.local.CartLocalDataSource
import com.luckyfrog.quickmart.features.cart.data.datasources.local.CartLocalDataSourceImpl
import com.luckyfrog.quickmart.features.cart.data.datasources.local.dao.CartDao
import com.luckyfrog.quickmart.features.cart.data.repositories.CartLocalRepositoryImpl
import com.luckyfrog.quickmart.features.cart.domain.repositories.CartLocalRepository
import com.luckyfrog.quickmart.features.cart.domain.usecases.local.CalculateSubtotalUseCase
import com.luckyfrog.quickmart.features.cart.domain.usecases.local.DeleteCartItemUseCase
import com.luckyfrog.quickmart.features.cart.domain.usecases.local.GetCartItemsUseCase
import com.luckyfrog.quickmart.features.cart.domain.usecases.local.GetSelectedCartItemsUseCase
import com.luckyfrog.quickmart.features.cart.domain.usecases.local.InsertCartItemUseCase
import com.luckyfrog.quickmart.features.cart.domain.usecases.local.UpdateCartItemUseCase
import com.luckyfrog.quickmart.features.category.data.datasources.remote.CategoryApi
import com.luckyfrog.quickmart.features.category.data.datasources.remote.CategoryRemoteDataSource
import com.luckyfrog.quickmart.features.category.data.datasources.remote.CategoryRemoteDataSourceImpl
import com.luckyfrog.quickmart.features.category.data.repositories.CategoryRepositoryImpl
import com.luckyfrog.quickmart.features.category.domain.repositories.CategoryRepository
import com.luckyfrog.quickmart.features.category.domain.usecases.GetCategoriesUseCase
import com.luckyfrog.quickmart.features.product.data.datasources.remote.ProductApi
import com.luckyfrog.quickmart.features.product.data.datasources.remote.ProductRemoteDataSource
import com.luckyfrog.quickmart.features.product.data.datasources.remote.ProductRemoteDataSourceImpl
import com.luckyfrog.quickmart.features.product.data.repositories.ProductRepositoryImpl
import com.luckyfrog.quickmart.features.product.domain.repositories.ProductRepository
import com.luckyfrog.quickmart.features.product.domain.usecases.GetProductDetailUseCase
import com.luckyfrog.quickmart.features.product.domain.usecases.GetProductsUseCase
import com.luckyfrog.quickmart.features.profile.data.datasources.remote.ProfileApi
import com.luckyfrog.quickmart.features.profile.data.datasources.remote.ProfileRemoteDataSource
import com.luckyfrog.quickmart.features.profile.data.datasources.remote.ProfileRemoteDataSourceImpl
import com.luckyfrog.quickmart.features.profile.data.repositories.ProfileRepositoryImpl
import com.luckyfrog.quickmart.features.profile.domain.repositories.ProfileRepository
import com.luckyfrog.quickmart.features.profile.domain.usecases.ChangePasswordUseCase
import com.luckyfrog.quickmart.features.profile.domain.usecases.CheckPasswordUseCase
import com.luckyfrog.quickmart.features.wishlist.data.datasources.local.WishlistAppDatabase
import com.luckyfrog.quickmart.features.wishlist.data.datasources.local.WishlistLocalDataSource
import com.luckyfrog.quickmart.features.wishlist.data.datasources.local.WishlistLocalDataSourceImpl
import com.luckyfrog.quickmart.features.wishlist.data.datasources.local.dao.WishlistDao
import com.luckyfrog.quickmart.features.wishlist.data.repositories.WishlistLocalRepositoryImpl
import com.luckyfrog.quickmart.features.wishlist.domain.repositories.WishlistLocalRepository
import com.luckyfrog.quickmart.features.wishlist.domain.usecases.local.DeleteWishlistItemUseCase
import com.luckyfrog.quickmart.features.wishlist.domain.usecases.local.GetWishlistItemsUseCase
import com.luckyfrog.quickmart.features.wishlist.domain.usecases.local.InsertWishlistItemUseCase
import com.luckyfrog.quickmart.utils.TokenManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideCartAppDatabase(@ApplicationContext context: Context): CartAppDatabase {
        return Room.databaseBuilder(
            context.applicationContext,  // Use applicationContext
            CartAppDatabase::class.java,
            "cart_database"  // Changed database name for clarity
        )
            .fallbackToDestructiveMigration()  // Optional: adds migration strategy
            .build()
    }

    @Provides
    @Singleton
    fun provideCartDao(database: CartAppDatabase): CartDao {
        return database.cartDao()
    }

    @Provides
    @Singleton
    fun provideWishlistAppDatabase(@ApplicationContext context: Context): WishlistAppDatabase {
        return Room.databaseBuilder(
            context.applicationContext,  // Use applicationContext
            WishlistAppDatabase::class.java,
            "wishlist_database"  // Changed database name for clarity
        )
            .fallbackToDestructiveMigration()  // Optional: adds migration strategy
            .build()
    }

    @Provides
    @Singleton
    fun provideWishlistDao(database: WishlistAppDatabase): WishlistDao {
        return database.wishlistDao()
    }
}

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

    @Singleton
    @Provides
    fun providesCartLocalDataSource(
        cartDao: CartDao
    ): CartLocalDataSource {
        return CartLocalDataSourceImpl(cartDao)
    }

    @Singleton
    @Provides
    fun providesWishlistLocalDataSource(
        wishlistDao: WishlistDao
    ): WishlistLocalDataSource {
        return WishlistLocalDataSourceImpl(wishlistDao)
    }

    @Singleton
    @Provides
    fun providesProfileRemoteDataSource(
        api: ProfileApi
    ): ProfileRemoteDataSource {
        return ProfileRemoteDataSourceImpl(api)
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

    @Singleton
    @Provides
    fun providesCartLocalRepository(
        localDataSource: CartLocalDataSource
    ): CartLocalRepository {
        return CartLocalRepositoryImpl(localDataSource)
    }

    @Singleton
    @Provides
    fun providesWishlistLocalRepository(
        localDataSource: WishlistLocalDataSource
    ): WishlistLocalRepository {
        return WishlistLocalRepositoryImpl(localDataSource)
    }

    @Singleton
    @Provides
    fun providesProfileRepository(
        remoteDataSource: ProfileRemoteDataSource
    ): ProfileRepository {
        return ProfileRepositoryImpl(remoteDataSource)
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
    fun providesVerifyOTPUseCase(
        repository: AuthRepository
    ): VerifyOTPUseCase {
        return VerifyOTPUseCase(repository)
    }

    @Singleton
    @Provides
    fun providesForgotPasswordSendOTPUseCase(
        repository: AuthRepository
    ): ForgotPasswordSendOTPUseCase {
        return ForgotPasswordSendOTPUseCase(repository)
    }

    @Singleton
    @Provides
    fun providesForgotPasswordVerifyOTPUseCase(
        repository: AuthRepository
    ): ForgotPasswordVerifyOTPUseCase {
        return ForgotPasswordVerifyOTPUseCase(repository)
    }

    @Singleton
    @Provides
    fun providesForgotPasswordChangePasswordUseCase(
        repository: AuthRepository
    ): ForgotPasswordChangePasswordUseCase {
        return ForgotPasswordChangePasswordUseCase(repository)
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

    /// Category
    @Singleton
    @Provides
    fun providesGetCategoriesUseCase(
        repository: CategoryRepository
    ): GetCategoriesUseCase {
        return GetCategoriesUseCase(repository)
    }


    /// Cart Local
    @Singleton
    @Provides
    fun providesInsertCartItemUseCase(
        repository: CartLocalRepository
    ): InsertCartItemUseCase {
        return InsertCartItemUseCase(repository)

    }

    @Singleton
    @Provides
    fun providesUpdateCartItemUseCase(
        repository: CartLocalRepository
    ): UpdateCartItemUseCase {
        return UpdateCartItemUseCase(repository)
    }

    @Singleton
    @Provides
    fun providesDeleteCartItemUseCase(
        repository: CartLocalRepository
    ): DeleteCartItemUseCase {
        return DeleteCartItemUseCase(repository)
    }

    @Singleton
    @Provides
    fun providesGetCartItemsUseCase(
        repository: CartLocalRepository
    ): GetCartItemsUseCase {
        return GetCartItemsUseCase(repository)
    }

    @Singleton
    @Provides
    fun providesGetSelectedCartItemsUseCase(
        repository: CartLocalRepository
    ): GetSelectedCartItemsUseCase {
        return GetSelectedCartItemsUseCase(repository)
    }

    @Singleton
    @Provides
    fun providesCalculateSubtotalUseCase(
        repository: CartLocalRepository
    ): CalculateSubtotalUseCase {
        return CalculateSubtotalUseCase(repository)
    }

    /// Wishlist Local
    @Singleton
    @Provides
    fun providesInsertWishlistItemUseCase(
        repository: WishlistLocalRepository
    ): InsertWishlistItemUseCase {
        return InsertWishlistItemUseCase(repository)

    }

    @Singleton
    @Provides
    fun providesDeleteWishlistItemUseCase(
        repository: WishlistLocalRepository
    ): DeleteWishlistItemUseCase {
        return DeleteWishlistItemUseCase(repository)
    }

    @Singleton
    @Provides
    fun providesGetWishlistItemsUseCase(
        repository: WishlistLocalRepository
    ): GetWishlistItemsUseCase {
        return GetWishlistItemsUseCase(repository)
    }


    /// Profile
    @Singleton
    @Provides
    fun providesCheckPasswordUseCase(
        repository: ProfileRepository
    ): CheckPasswordUseCase {
        return CheckPasswordUseCase(repository)
    }

    @Singleton
    @Provides
    fun providesChangePasswordUseCase(
        repository: ProfileRepository
    ): ChangePasswordUseCase {
        return ChangePasswordUseCase(repository)
    }
}

