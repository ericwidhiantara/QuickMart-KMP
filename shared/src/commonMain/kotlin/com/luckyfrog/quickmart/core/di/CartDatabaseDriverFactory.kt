package com.luckyfrog.quickmart.core.di

import app.cash.sqldelight.db.SqlDriver

expect class CartDatabaseDriverFactory {
    suspend fun createDriver(): SqlDriver
}