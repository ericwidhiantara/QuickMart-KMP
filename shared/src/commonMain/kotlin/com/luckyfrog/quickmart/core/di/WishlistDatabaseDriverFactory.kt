package com.luckyfrog.quickmart.core.di

import app.cash.sqldelight.db.SqlDriver

expect class WishlistDatabaseDriverFactory {
    suspend fun createDriver(): SqlDriver
}