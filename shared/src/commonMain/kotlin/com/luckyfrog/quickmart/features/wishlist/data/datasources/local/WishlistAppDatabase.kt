package com.luckyfrog.quickmart.features.wishlist.data.datasources.local

import com.luckyfrog.quickmart.core.di.WishlistDatabaseDriverFactory
import com.luckyfrog.quickmart.wishlist.WishlistDatabase

class WishlistAppDatabase(
    private val driverProvider: WishlistDatabaseDriverFactory,
) {
    private var database: WishlistDatabase? = null

    private suspend fun initDatabase() {
        if (database == null) {
            val driver = driverProvider.createDriver()
            println("Database driver: $driver")

            // Check if table exists
            val tableExists = driver.executeQuery(
                identifier = null,
                sql = "SELECT name FROM sqlite_master WHERE type='table' AND name='wishlist';",
                parameters = 0,
                mapper = { it.next() },
            )

            val hasTable = tableExists.value
            println("Wishlist table exists: $hasTable")

            // Only create schema if table doesn't exist
            if (!hasTable) {
                WishlistDatabase.Schema.create(driver)
                println("Database schema created")
            } else {
                println("Database schema already exists")
            }

            database = WishlistDatabase(driver)
            println("Database created: $database")
        }
    }

    suspend fun <R> invoke(block: suspend (WishlistDatabase) -> R): R {
        initDatabase()
        return block(database!!)
    }

}