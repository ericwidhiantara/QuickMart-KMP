package com.luckyfrog.quickmart.core.di

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.luckyfrog.quickmart.wishlist.WishlistDatabase

actual class WishlistDatabaseDriverFactory(private val context: Context) {
    actual suspend fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(
            schema = WishlistDatabase.Schema,
            context = context,
            name = "wishlist.db"
        )
    }
}