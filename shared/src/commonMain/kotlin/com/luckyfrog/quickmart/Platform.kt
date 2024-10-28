package com.luckyfrog.quickmart

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform