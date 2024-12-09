package com.luckyfrog.quickmart.features.cart.data.datasources.local

import com.luckyfrog.quickmart.carts.CartDatabase
import com.luckyfrog.quickmart.core.di.DatabaseDriverFactory

class CartAppDatabase(
    private val driverProvider: DatabaseDriverFactory,
) {
    private var database: CartDatabase? = null

    private suspend fun initDatabase() {
        if (database == null) {
            database = CartDatabase.invoke(
                driverProvider.createDriver(),
            )
        }
    }

    suspend operator fun <R> invoke(block: suspend (CartDatabase) -> R): R {
        initDatabase()
        return block(database!!)
    }

    val dbQuery = database?.cartDatabaseQueries

}