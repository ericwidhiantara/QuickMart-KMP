package com.luckyfrog.quickmart.core.di

import com.luckyfrog.quickmart.utils.provideDispatcher
import org.koin.dsl.module


val dispatcherModule = module {
    factory { provideDispatcher() }
}

private val sharedModules = listOf(
    ktorModule,
    apiModule,
    platformAppModule(),
    dataSourceModule,
    repositoryModule,
    dispatcherModule,
    useCaseModule,
    viewModelModule,
    sqlDelightModule,
)

fun getSharedModules() = sharedModules
