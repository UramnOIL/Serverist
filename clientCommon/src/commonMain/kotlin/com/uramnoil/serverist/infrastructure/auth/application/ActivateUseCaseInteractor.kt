package com.uramnoil.serverist.infrastructure.auth.application

import com.uramnoil.serverist.application.auth.ActivateUseCaseInput
import com.uramnoil.serverist.application.auth.ActivateUseCaseInputPort
import com.uramnoil.serverist.application.auth.ActivateUseCaseOutput
import com.uramnoil.serverist.application.auth.ActivateUseCaseOutputPort
import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.client.statement.HttpStatement
import io.ktor.http.ContentType
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import kotlin.coroutines.CoroutineContext

@Serializable
private class Credential(val email: String, val activationCode: String)

class ActivateUseCaseInteractor(
    coroutineContext: CoroutineContext,
    private val httpClient: HttpClient,
    private val host: String,
    private val outputPort: ActivateUseCaseOutputPort,
) : ActivateUseCaseInputPort, CoroutineScope by CoroutineScope(coroutineContext) {
    override fun execute(input: ActivateUseCaseInput) {
        launch {
            val result = runCatching {
                val (email, activationCode) = input
                val response = httpClient.post<HttpStatement>("$host/activate") {
                    method = HttpMethod.Post
                    contentType(ContentType.Application.Json)
                    body = Credential(email, activationCode)
                }.execute()

                when (response.status) {
                    HttpStatusCode.OK -> {
                    }
                    else -> {
                        throw IllegalArgumentException("")
                    }
                }
            }

            outputPort.handle(ActivateUseCaseOutput(result))
        }
    }
}
