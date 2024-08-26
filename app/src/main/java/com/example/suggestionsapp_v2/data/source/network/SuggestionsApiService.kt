package com.example.suggestionsapp_v2.data.source.network

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.HttpResponseValidator
import io.ktor.client.plugins.ResponseException
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.kotlinx.json.json


fun interface SuggestionsApiService {

    suspend fun getResponses(): NetworkResponseForms

    companion object {
        fun create(): SuggestionsApiService {
            return FormApiService(
                client = HttpClient(Android){
                    install(ContentNegotiation) { json() }
                }
            )
        }
    }
}

class FormApiService(
    private val client: HttpClient = HttpClient(CIO) {
        install(ContentNegotiation) { json() }
    }
) : SuggestionsApiService {
    override suspend fun getResponses(): NetworkResponseForms {
        return try {
            val responseCode: NetworkResponseForms = client.get(BASE_URL).body()
            responseCode
        } catch (e: Exception) {
            NetworkResponseForms(data = emptyList())
        }
    }
}


suspend fun testktor() {
    val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json()
        }
        HttpResponseValidator {
            handleResponseExceptionWithRequest { exception, request ->
                val clientException = exception as? ClientRequestException
                    ?: return@handleResponseExceptionWithRequest
                val exceptionResponse = clientException.response
                if (exceptionResponse.status == HttpStatusCode.NotFound) {
                    val exceptionResponseText = exceptionResponse.bodyAsText()
                    throw MissingPageException(exceptionResponse, exceptionResponseText)
                }
            }
        }
    }
    val response_code: HttpResponse = client.get(BASE_URL)
    val response: NetworkResponseForms = response_code.body()
    println(response.data.first())
}


private const val BASE_URL =
    "https://script.google.com/macros/s/AKfycbw522lH3IaPoTh6F-FKz1fyLKsrRKDAjeeIEF9QJ5cl7Zr1iBtDUYhTZv3oWRP1Gpwa6Q/exec"

class MissingPageException(response: HttpResponse, cachedResponseText: String) :
    ResponseException(response, cachedResponseText) {
    override val message: String =
        "Missing page: ${response.call.request.url}. " + "Status: ${response.status}."
}


//    retrofit approach ta ta bye bye

//}object SuggestionsAPI {
//    val retrofitService: SuggestionsApiService by lazy {
//        retrofit.create(SuggestionsApiService::class.java)
//    }
//}

//private val retrofit = Retrofit.Builder()
//    .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
//    .baseUrl(BASE_URL).build()
