package com.luckyfrog.quickmart.features.auth.domain.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey(autoGenerate = true) val id: Long?,
    @ColumnInfo(name = "first_name") val firstName: String?,
    @ColumnInfo(name = "last_name") val lastName: String?,
    @ColumnInfo(name = "maiden_name") val maidenName: String?,
    @ColumnInfo(name = "age") val age: Long?,
    @ColumnInfo(name = "gender") val gender: String?,
    @ColumnInfo(name = "email") val email: String?,
    @ColumnInfo(name = "phone") val phone: String?,
    @ColumnInfo(name = "username") val username: String?,
    @ColumnInfo(name = "password") val password: String?,
    @ColumnInfo(name = "birth_date") val birthDate: String?,
    @ColumnInfo(name = "image") val image: String?,
    @ColumnInfo(name = "blood_group") val bloodGroup: String?,
    @ColumnInfo(name = "height") val height: Double?,
    @ColumnInfo(name = "weight") val weight: Double?,
    @ColumnInfo(name = "eye_color") val eyeColor: String?,
    @ColumnInfo(name = "hair") val hair: HairEntity?,
    @ColumnInfo(name = "ip") val ip: String?,
    @ColumnInfo(name = "address") val address: AddressEntity?,
    @ColumnInfo(name = "mac_address") val macAddress: String?,
    @ColumnInfo(name = "university") val university: String?,
    @ColumnInfo(name = "bank") val bank: BankEntity?,
    @ColumnInfo(name = "company") val company: CompanyEntity?,
    @ColumnInfo(name = "ein") val ein: String?,
    @ColumnInfo(name = "ssn") val ssn: String?,
    @ColumnInfo(name = "user_agent") val userAgent: String?,
    @ColumnInfo(name = "crypto") val crypto: CryptoEntity?,
    @ColumnInfo(name = "role") val role: String?
)

@Entity(tableName = "user_address")
data class AddressEntity(
    @PrimaryKey(autoGenerate = true) val id: Int?,
    @ColumnInfo(name = "address") val address: String?,
    @ColumnInfo(name = "city") val city: String?,
    @ColumnInfo(name = "state") val state: String?,
    @ColumnInfo(name = "state_code") val stateCode: String?,
    @ColumnInfo(name = "postal_code") val postalCode: String?,
    @ColumnInfo(name = "coordinates") val coordinates: CoordinatesEntity?,
    @ColumnInfo(name = "country") val country: String?
)

@Entity(tableName = "coordinates")
data class CoordinatesEntity(
    @PrimaryKey(autoGenerate = true) val id: Int?,
    @ColumnInfo(name = "latitude") val lat: Double?,
    @ColumnInfo(name = "longitude") val lng: Double?
)

@Entity(tableName = "banks")
data class BankEntity(
    @PrimaryKey(autoGenerate = true) val id: Int?,
    @ColumnInfo(name = "card_expire") val cardExpire: String?,
    @ColumnInfo(name = "card_number") val cardNumber: String?,
    @ColumnInfo(name = "card_type") val cardType: String?,
    @ColumnInfo(name = "currency") val currency: String?,
    @ColumnInfo(name = "iban") val iban: String?
)


@Entity(tableName = "companies")
data class CompanyEntity(
    @PrimaryKey(autoGenerate = true) val id: Int?,
    @ColumnInfo(name = "department") val department: String?,
    @ColumnInfo(name = "name") val name: String?,
    @ColumnInfo(name = "title") val title: String?,
    @ColumnInfo(name = "address") val address: AddressEntity?
)

@Entity(tableName = "cryptos")
data class CryptoEntity(
    @PrimaryKey(autoGenerate = true) val id: Int?,
    @ColumnInfo(name = "coin") val coin: String?,
    @ColumnInfo(name = "wallet") val wallet: String?,
    @ColumnInfo(name = "network") val network: String?
)

@Entity(tableName = "hairs")
data class HairEntity(
    @PrimaryKey(autoGenerate = true) val id: Int?,
    @ColumnInfo(name = "color") val color: String?,
    @ColumnInfo(name = "type") val type: String?
)