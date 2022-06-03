package com.uramnoil.serverist.infrastructure.auth.application

import com.uramnoil.serverist.application.auth.SignUpUseCaseInput
import com.uramnoil.serverist.application.auth.SignUpUseCaseInputPort
import com.uramnoil.serverist.application.auth.SignUpUseCaseOutput
import com.uramnoil.serverist.application.auth.SignUpUseCaseOutputPort
import com.uramnoil.serverist.exceptions.BadRequestException
import com.uramnoil.serverist.exceptions.InternalServerErrorException
import io.ktor.client.HttpClient
import io.ktor.client.call.receive
import io.ktor.client.request.post
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import kotlin.coroutines.CoroutineContext

class SignUpUseCaseInteractor(
    private val coroutineContext: CoroutineContext,
    private val host: String,
    private val httpClient: HttpClient,
    private val outputPort: SignUpUseCaseOutputPort,
) : SignUpUseCaseInputPort {
    @Serializable
    data class Credential(val email: String, val password: String)

    override fun execute(input: SignUpUseCaseInput) {
        val (email, password) = input
        CoroutineScope(coroutineContext).launch {
            val result = kotlin.runCatching {
                val response = httpClient.post<HttpResponse>("$host/signup") {
                    method = HttpMethod.Post
                    contentType(ContentType.Application.Json)
                    body = Credential(email, password)
                }

                val content = response.receive<String>()

                when (response.status) {
                    HttpStatusCode.OK -> {}
                    HttpStatusCode.BadRequest -> {
                        throw BadRequestException(content)
                    }
                    else -> {
                        throw InternalServerErrorException(content)
                    }
                }
            }

            outputPort.handle(SignUpUseCaseOutput(result))
        }
    }
}
