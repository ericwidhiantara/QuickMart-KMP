package com.luckyfrog.quickmart.features.auth.domain.entities.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.luckyfrog.quickmart.features.auth.domain.entities.AddressEntity
import com.luckyfrog.quickmart.features.auth.domain.entities.BankEntity
import com.luckyfrog.quickmart.features.auth.domain.entities.CompanyEntity
import com.luckyfrog.quickmart.features.auth.domain.entities.CoordinatesEntity
import com.luckyfrog.quickmart.features.auth.domain.entities.CryptoEntity
import com.luckyfrog.quickmart.features.auth.domain.entities.HairEntity
import com.luckyfrog.quickmart.features.auth.domain.entities.UserEntity

@Dao
interface UserDao {

    @Query("SELECT * FROM users ORDER BY id ASC")
    fun getAllUsers(): List<UserEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(user: UserEntity)

    @Query("DELETE FROM users")
    suspend fun deleteAll()
}

@Dao
interface AddressDao {

    @Query("SELECT * FROM user_address ORDER BY address ASC")
    fun getAllAddresses(): List<AddressEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(address: AddressEntity)

    @Query("DELETE FROM user_address")
    suspend fun deleteAll()
}


@Dao
interface CoordinatesDao {

    @Query("SELECT * FROM coordinates")
    fun getAllCoordinates(): List<CoordinatesEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(coordinates: CoordinatesEntity)

    @Query("DELETE FROM coordinates")
    suspend fun deleteAll()
}

@Dao
interface BankDao {

    @Query("SELECT * FROM banks ORDER BY card_number ASC")
    fun getAllBanks(): List<BankEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(bank: BankEntity)

    @Query("DELETE FROM banks")
    suspend fun deleteAll()
}

@Dao
interface CompanyDao {

    @Query("SELECT * FROM companies ORDER BY name ASC")
    fun getAllCompanies(): List<CompanyEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(company: CompanyEntity)

    @Query("DELETE FROM companies")
    suspend fun deleteAll()
}

@Dao
interface CryptoDao {

    @Query("SELECT * FROM cryptos ORDER BY coin ASC")
    fun getAllCryptos(): List<CryptoEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(crypto: CryptoEntity)

    @Query("DELETE FROM cryptos")
    suspend fun deleteAll()
}

@Dao
interface HairDao {

    @Query("SELECT * FROM hairs ORDER BY color ASC")
    fun getAllHairs(): List<HairEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(hair: HairEntity)

    @Query("DELETE FROM hairs")
    suspend fun deleteAll()
}