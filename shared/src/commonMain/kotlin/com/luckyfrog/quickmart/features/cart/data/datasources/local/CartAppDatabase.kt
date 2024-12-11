package com.luckyfrog.quickmart.features.cart.data.datasources.local

import com.luckyfrog.quickmart.carts.CartDatabase
import com.luckyfrog.quickmart.core.di.CartDatabaseDriverFactory

class CartAppDatabase(
    private val driverProvider: CartDatabaseDriverFactory,
) {
    private var database: CartDatabase? = null

    private suspend fun initDatabase() {
        if (database == null) {
            val driver = driverProvider.createDriver()
            println("Database driver: $driver")

            // Check if table exists
            val tableExists = driver.executeQuery(
                identifier = null,
                sql = "SELECT name FROM sqlite_master WHERE type='table' AND name='carts';",
                parameters = 0,
                mapper = { it.next() },
            )

            val hasTable = tableExists.value
            println("Cart table exists: $hasTable")

            // Only create schema if table doesn't exist
            if (!hasTable) {
                CartDatabase.Schema.create(driver)
                println("Database schema created")
            } else {
                println("Database schema already exists")
            }

            database = CartDatabase(driver)
            println("Database created: $database")
        }
    }

    suspend fun <R> invoke(block: suspend (CartDatabase) -> R): R {
        initDatabase()
        return block(database!!)
    }

}