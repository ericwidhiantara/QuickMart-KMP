package com.luckyfrog.quickmart.core.di

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import com.luckyfrog.quickmart.wishlist.WishlistDatabase

actual class WishlistDatabaseDriverFactory {
    actual suspend fun createDriver(): SqlDriver {
        return NativeSqliteDriver(WishlistDatabase.Schema, "wishlist.db")
    }
}