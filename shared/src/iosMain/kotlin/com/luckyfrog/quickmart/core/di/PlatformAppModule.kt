package com.luckyfrog.quickmart.core.di

import org.koin.core.module.Module
import org.koin.dsl.module

actual fun platformAppModule(): Module = module {
    single { DatabaseDriverFactory() }

}
