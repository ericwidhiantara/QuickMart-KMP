package com.luckyfrog.quickmart.features.auth.data.models.response

data class LoginFormRequestDto(val username: String, val password: String, val expiresInMins: Int)