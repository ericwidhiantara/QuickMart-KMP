package com.luckyfrog.quickmart.core.di

import kotlinx.coroutines.Dispatchers
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

fun initKoin(appDeclaration: KoinAppDeclaration = {}) =
    startKoin {
        appDeclaration()
        modules(
            ktorModule,
            apiModule,
            dataSourceModule,
            repositoryModule,
            dispatcherModule,
            useCaseModule,
            platformViewModelModule(),
            sqlDelightModule,
        )
    }

val dispatcherModule = module {
    factory { Dispatchers.Default }
}


