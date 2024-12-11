package com.luckyfrog.quickmart.core.di

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.luckyfrog.quickmart.carts.CartDatabase

actual class CartDatabaseDriverFactory(private val context: Context) {
    actual suspend fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(
            schema = CartDatabase.Schema,
            context = context,
            name = "carts.db"
        )
    }
}