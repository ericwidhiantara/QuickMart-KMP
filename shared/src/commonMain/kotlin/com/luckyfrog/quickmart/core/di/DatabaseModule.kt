package com.luckyfrog.quickmart.core.di

import com.luckyfrog.quickmart.features.cart.data.datasources.local.CartAppDatabase
import com.luckyfrog.quickmart.features.wishlist.data.datasources.local.WishlistAppDatabase
import org.koin.dsl.module

val sqlDelightModule = module {
    single { CartAppDatabase(get()) }
    
    single { WishlistAppDatabase(get()) }
}
