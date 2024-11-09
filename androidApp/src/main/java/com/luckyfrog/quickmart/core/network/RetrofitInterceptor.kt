package com.luckyfrog.quickmart.core.network

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import org.json.JSONObject
import java.io.IOException
import java.nio.charset.Charset

class RetrofitInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()

        // Log request info
        logRequest(request)

        val response: Response = try {
            chain.proceed(request)
        } catch (e: IOException) {
            // Handle network error logging
            e.printStackTrace()
            throw e
        }

        // Log response info
        return logResponse(response)
    }

    private fun logRequest(request: Request) {
        val headers = request.headers
        val headerMessage =
            headers.joinToString(separator = "\n") { header -> "► ${header.first}: ${header.second}" }
        val requestUrl = "${request.url}"

        try {
            val method = request.method.uppercase()
            val queryParams = request.url.queryParameterNames
                .joinToString(separator = "\n") { param ->
                    "► $param: ${
                        request.url.queryParameter(
                            param
                        )
                    }"
                }

            val body = request.body
            val bodyString = body?.let {
                val buffer = okio.Buffer()
                body.writeTo(buffer)
                buffer.readUtf8()
            } ?: ""

            // Pretty print JSON body if exists
            val prettyBody = if (bodyString.isNotEmpty() && body?.contentType()
                    .toString() == "application/json"
            ) {
                JSONObject(bodyString).toString(2)
            } else {
                bodyString
            }

            println("REQUEST ► $method $requestUrl\n\nHeaders:\n$headerMessage\n\nQueryParameters:\n$queryParams\n\nBody:\n$prettyBody")
        } catch (e: Exception) {
            println("Failed to log request: ${e.message}")
        }
    }

    private fun logResponse(response: Response): Response {
        val responseBody = response.body
        val contentType = responseBody?.contentType()
        val responseBodyString = responseBody?.string() ?: ""

        val prettyJson = try {
            JSONObject(responseBodyString).toString(2)
        } catch (e: Exception) {
            responseBodyString
        }

        val responseUrl = response.request.url.toString()
        val headerMessage =
            response.headers.joinToString(separator = "\n") { header -> "► ${header.first}: ${header.second}" }

        println("RESPONSE ◀ ${response.code} $responseUrl\n\nHeaders:\n$headerMessage\n\nResponse:\n$prettyJson")

        // Rebuild the response before returning it because the response body can only be read once
        return response.newBuilder()
            .body(
                responseBodyString.toByteArray(Charset.forName("UTF-8")).toResponseBody(contentType)
            )
            .build()
    }
}
