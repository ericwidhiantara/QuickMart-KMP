package com.luckyfrog.quickmart.core.di

import com.luckyfrog.quickmart.utils.Constants
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.cache.*
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.HttpMethod
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.dsl.module

val networkModule = module {
    single { provideHttpClient() }
    single { Constants.SERVER_URL }
}


fun provideHttpClient() = HttpClient(CIO) {
    install(ContentNegotiation) {
        json(Json {
            ignoreUnknownKeys = true
            useAlternativeNames = true
            coerceInputValues = true
        })
    }
    install(Logging) {
        logger = Logger.DEFAULT
        level = LogLevel.ALL
        logger = object : Logger {
            override fun log(message: String) {
                println(message)
            }
        }
        filter {
            it.method == HttpMethod.Post
        }
    }

    install(HttpTimeout) {
        requestTimeoutMillis = 15000L
        connectTimeoutMillis = 15000L
        socketTimeoutMillis = 15000L
    }

    // Apply to all requests
    defaultRequest {
        // Parameter("api_key", "some_api_key")
        // Content Type
        /*if ( != HttpMethod.Get) contentType(ContentType.Application.Json)
        accept(ContentType.Application.Json)*/
//        headers.set()
    }
}