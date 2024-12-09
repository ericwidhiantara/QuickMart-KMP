package com.luckyfrog.quickmart.core.di

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import com.luckyfrog.quickmart.carts.CartDatabase

actual class DatabaseDriverFactory {
    actual suspend fun createDriver(): SqlDriver {
        return NativeSqliteDriver(CartDatabase.Schema, "test.db")
    }
}