package com.luckyfrog.quickmart.features.auth.data.models.response

import kotlinx.serialization.SerialName

data class UserResponseDto(
    @SerialName("id") val id: Long?,
    @SerialName("firstName") val firstName: String?,
    @SerialName("lastName") val lastName: String?,
    @SerialName("maidenName") val maidenName: String?,
    @SerialName("age") val age: Long?,
    @SerialName("gender") val gender: String?,
    @SerialName("email") val email: String?,
    @SerialName("phone") val phone: String?,
    @SerialName("username") val username: String?,
    @SerialName("password") val password: String?,
    @SerialName("birthDate") val birthDate: String?,
    @SerialName("image") val image: String?,
    @SerialName("bloodGroup") val bloodGroup: String?,
    @SerialName("height") val height: Double?,
    @SerialName("weight") val weight: Double?,
    @SerialName("eyeColor") val eyeColor: String?,
    @SerialName("hair") val hair: HairResponseDto?,
    @SerialName("ip") val ip: String?,
    @SerialName("address") val address: AddressResponseDto?,
    @SerialName("macAddress") val macAddress: String?,
    @SerialName("university") val university: String?,
    @SerialName("bank") val bank: BankResponseDto?,
    @SerialName("company") val company: CompanyResponseDto?,
    @SerialName("ein") val ein: String?,
    @SerialName("ssn") val ssn: String?,
    @SerialName("userAgent") val userAgent: String?,
    @SerialName("crypto") val crypto: CryptoResponseDto?,
    @SerialName("role") val role: String?
)

data class AddressResponseDto(
    @SerialName("address") val address: String?,
    @SerialName("city") val city: String?,
    @SerialName("state") val state: String?,
    @SerialName("stateCode") val stateCode: String?,
    @SerialName("postalCode") val postalCode: String?,
    @SerialName("coordinates") val coordinates: CoordinatesResponseDto?,
    @SerialName("country") val country: String?
)

data class CoordinatesResponseDto(
    @SerialName("lat") val lat: Double?,
    @SerialName("lng") val lng: Double?
)

data class BankResponseDto(
    @SerialName("cardExpire") val cardExpire: String?,
    @SerialName("cardNumber") val cardNumber: String?,
    @SerialName("cardType") val cardType: String?,
    @SerialName("currency") val currency: String?,
    @SerialName("iban") val iban: String?
)

data class CompanyResponseDto(
    @SerialName("department") val department: String?,
    @SerialName("name") val name: String?,
    @SerialName("title") val title: String?,
    @SerialName("address") val address: AddressResponseDto?
)

data class CryptoResponseDto(
    @SerialName("coin") val coin: String?,
    @SerialName("wallet") val wallet: String?,
    @SerialName("network") val network: String?
)

data class HairResponseDto(
    @SerialName("color") val color: String?,
    @SerialName("type") val type: String?
)
