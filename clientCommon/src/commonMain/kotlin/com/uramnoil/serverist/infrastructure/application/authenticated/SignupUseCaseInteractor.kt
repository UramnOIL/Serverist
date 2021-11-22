package com.uramnoil.serverist.infrastructure.application.authenticated

import com.uramnoil.serverist.application.auth.SignupUseCaseInputPort
import com.uramnoil.serverist.application.auth.SignupUseCaseOutputPort
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


class SignupUseCaseInteractor(
    private val host: String,
    private val httpClient: HttpClient,
    private val outputPort: SignupUseCaseOutputPort,
    private val coroutineContext: CoroutineContext
) : SignupUseCaseInputPort {
    @Serializable
    data class Credential(val email: String, val password: String)

    override fun execute(email: String, password: String) {
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

            outputPort.handle(result)
        }
    }
}