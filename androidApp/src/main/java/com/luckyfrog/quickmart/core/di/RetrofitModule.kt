package com.luckyfrog.quickmart.core.di

import android.content.Context
import com.luckyfrog.quickmart.core.network.AuthAuthenticator
import com.luckyfrog.quickmart.core.network.AuthInterceptor
import com.luckyfrog.quickmart.features.auth.data.datasources.remote.AuthApi
import com.luckyfrog.quickmart.features.category.data.datasources.remote.CategoryApi
import com.luckyfrog.quickmart.features.product.data.datasources.remote.ProductApi
import com.luckyfrog.quickmart.utils.TokenManager
import com.luckyfrog.quickmart.utils.helper.Constants
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {
    @Singleton
    @Provides
    fun provideTokenManager(@ApplicationContext context: Context): TokenManager =
        TokenManager(context)

    @Singleton
    @Provides
    fun provideAuthInterceptor(tokenManager: TokenManager): AuthInterceptor =
        AuthInterceptor(tokenManager)

    @Singleton
    @Provides
    fun provideAuthAuthenticator(tokenManager: TokenManager): AuthAuthenticator =
        AuthAuthenticator(tokenManager)

    // Provides Moshi for JSON parsing
    @Singleton
    @Provides
    fun providesMoshi(): Moshi = Moshi.Builder().build()

    // Provides OkHttpClient with necessary interceptors
    @Singleton
    @Provides
    fun providesOkHttpClient(
        authInterceptor: AuthInterceptor,
        authAuthenticator: AuthAuthenticator,
    ): OkHttpClient {
        val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        return OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .addInterceptor(httpLoggingInterceptor)
            .authenticator(authAuthenticator)
            .readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .build()
    }

    // Provides Retrofit instance
    @Singleton
    @Provides
    fun providesRetrofit(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(Constants.SERVER_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()

    // Provides AuthApi from the Retrofit instance
    @Singleton
    @Provides
    fun providesAuthApi(retrofit: Retrofit): AuthApi =
        retrofit.create(AuthApi::class.java)

    // Provides ProductApi from the Retrofit instance
    @Singleton
    @Provides
    fun providesProductApi(retrofit: Retrofit): ProductApi =
        retrofit.create(ProductApi::class.java)

    // Provides CategoryApi from the Retrofit instance
    @Singleton
    @Provides
    fun providesCategoryApi(retrofit: Retrofit): CategoryApi =
        retrofit.create(CategoryApi::class.java)
}
