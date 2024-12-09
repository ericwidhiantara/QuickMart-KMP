package com.luckyfrog.quickmart.core.di

import com.luckyfrog.quickmart.features.cart.data.datasources.local.CartAppDatabase
import org.koin.dsl.module

val sqlDelightModule = module {
    single { CartAppDatabase(get()) }
}
