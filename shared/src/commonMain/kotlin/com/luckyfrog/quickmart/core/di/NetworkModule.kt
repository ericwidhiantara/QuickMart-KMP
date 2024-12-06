package com.luckyfrog.quickmart.core.di

import com.luckyfrog.quickmart.core.preferences.StringSettingConfig
import com.luckyfrog.quickmart.features.auth.data.datasources.remote.AuthApi
import com.luckyfrog.quickmart.utils.Constants
import com.russhwolf.settings.Settings
import io.ktor.client.HttpClient
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.URLProtocol
import io.ktor.http.headers
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
                headers {
                    // Add the Bearer Token to the Authorization header
                    val settings = get<Settings>()
                    val config = StringSettingConfig(settings, Constants.ACCESS_TOKEN, "")
                    val token = config.get()
                    println("token from ktor: $token")
                    if (token.isNotEmpty()) {
                        append("Authorization", "Bearer $token")
                    }
                    append("Content-Type", "application/json")
                    append("Accept", "application/json")
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
            install(Auth) {
                bearer {
                    loadTokens {
                        val settings = get<Settings>()
                        val config = StringSettingConfig(settings, Constants.ACCESS_TOKEN, "")

                        val token = config.get()
                        println("token from ktor load token: $token")
                        
                        BearerTokens(
                            accessToken = token,
                            refreshToken = "",
                        )
                    }
                    refreshTokens {
                        val settings = get<Settings>()

                        // Configurations for tokens
                        val configAccessToken =
                            StringSettingConfig(settings, Constants.ACCESS_TOKEN, "")
                        val configRefreshToken =
                            StringSettingConfig(settings, Constants.REFRESH_TOKEN, "")

                        try {
                            // Attempt to refresh the token using the refresh token
                            val refreshToken = configRefreshToken.get()
                            val response = get<AuthApi>().postRefreshToken(refreshToken)

                            if (response.meta?.code == 200) {
                                // Successfully refreshed tokens; update the settings
                                val newAccessToken = response.data?.accessToken.orEmpty()
                                val newRefreshToken = response.data?.refreshToken.orEmpty()

                                configAccessToken.set(newAccessToken)
                                configRefreshToken.set(newRefreshToken)

                                // Return the new tokens to be used by Ktor
                                BearerTokens(
                                    accessToken = newAccessToken,
                                    refreshToken = newRefreshToken
                                )
                            } else {
                                // Refresh failed; clear tokens
                                configAccessToken.remove()
                                configRefreshToken.remove()
                                null // Return null to indicate failure
                            }
                        } catch (e: Exception) {
                            // Handle exceptions during token refresh; clear tokens
                            configAccessToken.remove()
                            configRefreshToken.remove()
                            null // Return null to indicate failure
                        }
                    }


                }
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