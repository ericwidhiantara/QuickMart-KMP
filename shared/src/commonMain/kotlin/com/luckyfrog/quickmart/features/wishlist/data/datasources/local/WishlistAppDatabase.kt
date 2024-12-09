package com.luckyfrog.quickmart.features.wishlist.data.datasources.local

import com.luckyfrog.quickmart.core.di.DatabaseDriverFactory
import com.luckyfrog.quickmart.wishlist.WishlistDatabase

class WishlistAppDatabase(
    private val driverProvider: DatabaseDriverFactory,
) {
    private var database: WishlistDatabase? = null

    private suspend fun initDatabase() {
        if (database == null) {
            database = WishlistDatabase.invoke(
                driverProvider.createDriver(),
            )
        }
    }

    suspend operator fun <R> invoke(block: suspend (WishlistDatabase) -> R): R {
        initDatabase()
        return block(database!!)
    }

    val dbQuery = database?.wishlistQueries

}