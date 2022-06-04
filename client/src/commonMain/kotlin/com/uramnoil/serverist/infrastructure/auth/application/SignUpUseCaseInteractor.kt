package com.uramnoil.serverist.infrastructure.auth.application

import com.uramnoil.serverist.SessionId
import com.uramnoil.serverist.application.auth.SignUpUseCaseInput
import com.uramnoil.serverist.application.auth.SignUpUseCaseInputPort
import com.uramnoil.serverist.application.auth.SignUpUseCaseOutput
import com.uramnoil.serverist.application.auth.SignUpUseCaseOutputPort
import com.uramnoil.serverist.exceptions.BadRequestException
import com.uramnoil.serverist.exceptions.InternalServerErrorException
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import kotlin.coroutines.CoroutineContext

class SignUpUseCaseInteractor(
    private val coroutineContext: CoroutineContext,
    private val sessionId: SessionId,
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
                val response = httpClient.post<HttpStatement>("$host/signup") {
                    method = HttpMethod.Post
                    contentType(ContentType.Application.Json)
                    body = Credential(email, password)
                }.execute()

                when (response.status) {
                    HttpStatusCode.OK -> {
                        sessionId.sessionId = response.headers["Auth"]
                    }
                    HttpStatusCode.BadRequest -> {
                        val content = response.receive<String>()
                        throw BadRequestException(content)
                    }
                    else -> {
                        val content = response.receive<String>()
                        throw InternalServerErrorException(content)
                    }
                }
            }

            outputPort.handle(SignUpUseCaseOutput(result))
        }
    }
}
