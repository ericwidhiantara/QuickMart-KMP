package com.luckyfrog.quickmart.features.auth.data.models.mapper

import com.luckyfrog.quickmart.features.auth.data.models.response.AddressResponseDto
import com.luckyfrog.quickmart.features.auth.data.models.response.BankResponseDto
import com.luckyfrog.quickmart.features.auth.data.models.response.CompanyResponseDto
import com.luckyfrog.quickmart.features.auth.data.models.response.CoordinatesResponseDto
import com.luckyfrog.quickmart.features.auth.data.models.response.CryptoResponseDto
import com.luckyfrog.quickmart.features.auth.data.models.response.HairResponseDto
import com.luckyfrog.quickmart.features.auth.data.models.response.UserResponseDto
import com.luckyfrog.quickmart.features.auth.domain.entities.AddressEntity
import com.luckyfrog.quickmart.features.auth.domain.entities.BankEntity
import com.luckyfrog.quickmart.features.auth.domain.entities.CompanyEntity
import com.luckyfrog.quickmart.features.auth.domain.entities.CoordinatesEntity
import com.luckyfrog.quickmart.features.auth.domain.entities.CryptoEntity
import com.luckyfrog.quickmart.features.auth.domain.entities.HairEntity
import com.luckyfrog.quickmart.features.auth.domain.entities.UserEntity

/**
 * Mapping from UserResponseDto to com.luckyfrog.quickmart.features.auth.domain.entities.UserEntity
 */
fun UserResponseDto.toEntity() = UserEntity(
    id = this.id ?: 0,
    firstName = this.firstName ?: "",
    lastName = this.lastName ?: "",
    maidenName = this.maidenName ?: "",
    age = this.age ?: 0,
    gender = this.gender ?: "",
    email = this.email ?: "",
    phone = this.phone ?: "",
    username = this.username ?: "",
    password = this.password ?: "",
    birthDate = this.birthDate ?: "",
    image = this.image ?: "",
    bloodGroup = this.bloodGroup ?: "",
    height = this.height ?: 0.0,
    weight = this.weight ?: 0.0,
    eyeColor = this.eyeColor ?: "",
    hair = this.hair?.toEntity() ?: HairEntity(0, "", ""),
    ip = this.ip ?: "",
    address = this.address?.toEntity() ?: AddressEntity(
        0,
        "",
        "",
        "",
        "",
        "",
        CoordinatesEntity(0, 0.0, 0.0),
        ""
    ),
    macAddress = this.macAddress ?: "",
    university = this.university ?: "",
    bank = this.bank?.toEntity() ?: BankEntity(0, "", "", "", "", ""),
    company = this.company?.toEntity() ?: CompanyEntity(
        0,
        "",
        "",
        "",
        AddressEntity(0, "", "", "", "", "", CoordinatesEntity(0, 0.0, 0.0), "")
    ),
    ein = this.ein ?: "",
    ssn = this.ssn ?: "",
    userAgent = this.userAgent ?: "",
    crypto = this.crypto?.toEntity() ?: CryptoEntity(0, "", "", ""),
    role = this.role ?: ""
)

/**
 * Mapping from com.luckyfrog.ecommerce.features.auth.domain.entities.UserEntity to UserResponseDto
 */
fun UserEntity.toDto() = UserResponseDto(
    id = this.id ?: 0,
    firstName = this.firstName ?: "",
    lastName = this.lastName ?: "",
    maidenName = this.maidenName ?: "",
    age = this.age ?: 0,
    gender = this.gender ?: "",
    email = this.email ?: "",
    phone = this.phone ?: "",
    username = this.username ?: "",
    password = this.password ?: "",
    birthDate = this.birthDate ?: "",
    image = this.image ?: "",
    bloodGroup = this.bloodGroup ?: "",
    height = this.height ?: 0.0,
    weight = this.weight ?: 0.0,
    eyeColor = this.eyeColor ?: "",
    hair = this.hair?.toDto() ?: HairResponseDto("", ""),
    ip = this.ip ?: "",
    address = this.address?.toDto() ?: AddressResponseDto(
        "",
        "",
        "",
        "",
        "",
        CoordinatesResponseDto(0.0, 0.0),
        ""
    ),
    macAddress = this.macAddress ?: "",
    university = this.university ?: "",
    bank = this.bank?.toDto() ?: BankResponseDto("", "", "", "", ""),
    company = this.company?.toDto() ?: CompanyResponseDto(
        "",
        "",
        "",
        AddressResponseDto("", "", "", "", "", CoordinatesResponseDto(0.0, 0.0), "")
    ),
    ein = this.ein ?: "",
    ssn = this.ssn ?: "",
    userAgent = this.userAgent ?: "",
    crypto = this.crypto?.toDto() ?: CryptoResponseDto("", "", ""),
    role = this.role ?: ""
)

/**
 * Mapping for HairResponseDto and com.luckyfrog.ecommerce.features.auth.domain.entities.HairEntity
 */
fun HairResponseDto.toEntity() = HairEntity(
    id = 0,
    color = this.color ?: "",
    type = this.type ?: ""
)

fun HairEntity.toDto() = HairResponseDto(
    color = this.color ?: "",
    type = this.type ?: ""
)

/**
 * Mapping for AddressResponseDto and com.luckyfrog.ecommerce.features.auth.domain.entities.AddressEntity
 */
fun AddressResponseDto.toEntity() = AddressEntity(
    id = 0,
    address = this.address ?: "",
    city = this.city ?: "",
    state = this.state ?: "",
    stateCode = this.stateCode ?: "",
    postalCode = this.postalCode ?: "",
    coordinates = this.coordinates?.toEntity() ?: CoordinatesEntity(id = 0, 0.0, 0.0),
    country = this.country ?: ""
)

fun AddressEntity.toDto() = AddressResponseDto(
    address = this.address ?: "",
    city = this.city ?: "",
    state = this.state ?: "",
    stateCode = this.stateCode ?: "",
    postalCode = this.postalCode ?: "",
    coordinates = this.coordinates?.toDto() ?: CoordinatesResponseDto(0.0, 0.0),
    country = this.country ?: ""
)

/**
 * Mapping for CoordinatesResponseDto and com.luckyfrog.ecommerce.features.auth.domain.entities.CoordinatesEntity
 */
fun CoordinatesResponseDto.toEntity() = CoordinatesEntity(
    id = 0,
    lat = this.lat ?: 0.0,
    lng = this.lng ?: 0.0
)

fun CoordinatesEntity.toDto() = CoordinatesResponseDto(
    lat = this.lat ?: 0.0,
    lng = this.lng ?: 0.0
)

/**
 * Mapping for BankResponseDto and com.luckyfrog.ecommerce.features.auth.domain.entities.BankEntity
 */
fun BankResponseDto.toEntity() = BankEntity(
    id = 0,
    cardExpire = this.cardExpire ?: "",
    cardNumber = this.cardNumber ?: "",
    cardType = this.cardType ?: "",
    currency = this.currency ?: "",
    iban = this.iban ?: ""
)

fun BankEntity.toDto() = BankResponseDto(
    cardExpire = this.cardExpire ?: "",
    cardNumber = this.cardNumber ?: "",
    cardType = this.cardType ?: "",
    currency = this.currency ?: "",
    iban = this.iban ?: ""
)

/**
 * Mapping for CompanyResponseDto and com.luckyfrog.ecommerce.features.auth.domain.entities.CompanyEntity
 */
fun CompanyResponseDto.toEntity() = CompanyEntity(
    id = 0,
    department = this.department ?: "",
    name = this.name ?: "",
    title = this.title ?: "",
    address = this.address?.toEntity(),
)

fun CompanyEntity.toDto() = CompanyResponseDto(
    department = this.department ?: "",
    name = this.name ?: "",
    title = this.title ?: "",
    address = this.address?.toDto() ?: AddressResponseDto(
        "",
        "",
        "",
        "",
        "",
        CoordinatesResponseDto(0.0, 0.0),
        ""
    )
)

/**
 * Mapping for CryptoResponseDto and com.luckyfrog.ecommerce.features.auth.domain.entities.CryptoEntity
 */
fun CryptoResponseDto.toEntity() = CryptoEntity(
    id = 0,
    coin = this.coin ?: "",
    wallet = this.wallet ?: "",
    network = this.network ?: ""
)

fun CryptoEntity.toDto() = CryptoResponseDto(
    coin = this.coin ?: "",
    wallet = this.wallet ?: "",
    network = this.network ?: ""
)

// Mapping lists
fun List<UserResponseDto>.toEntityList(): List<UserEntity> {
    return this.map { it.toEntity() }
}

fun List<UserEntity>.toDtoList(): List<UserResponseDto> {
    return this.map { it.toDto() }
}
