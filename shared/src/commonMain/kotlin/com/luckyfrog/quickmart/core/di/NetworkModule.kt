package com.luckyfrog.quickmart.core.di

import com.luckyfrog.quickmart.utils.Constants
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.URLProtocol
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.koin.dsl.module

class AdvancedCustomKtorLogger : Logger {
    override fun log(message: String) {
        when {
            message.startsWith("REQUEST") -> logRequest(message)
            message.startsWith("RESPONSE") -> logResponse(message)
            else -> println(message)
        }
    }

    private fun logRequest(requestMessage: String) {
        println("📤 REQUEST:")
        try {
            val formattedMessage = requestMessage.prettyPrintJson()
            println(formattedMessage)
        } catch (e: Exception) {
            println("Error formatting request: ${e.message}")
        }
    }

    private fun logResponse(responseMessage: String) {
        println("📥 RESPONSE:")
        try {
            val formattedMessage = responseMessage.prettyPrintJson()
            println(formattedMessage)
        } catch (e: Exception) {
            println("Error formatting response: ${e.message}")
        }
    }

}

val ktorModule = module {
    single {
        HttpClient {
            defaultRequest {
                host = Constants.SERVER_URL
                url {
                    protocol = URLProtocol.HTTPS
                }
            }
            install(ContentNegotiation) {
                json(
                    Json {
                        ignoreUnknownKeys = true
                        prettyPrint = true
                        isLenient = true
                    }
                )
            }
            install(Logging) {
                logger = AdvancedCustomKtorLogger()
                level = LogLevel.ALL
            }
        }
    }
}

private fun String.prettyPrintJson(): String {
    return try {
        val json = Json { prettyPrint = true }
        val jsonElement = json.parseToJsonElement(this)
        json.encodeToString(jsonElement)
    } catch (e: Exception) {
        this
    }
}