package com.uramnoil.serverist.infrastructure.auth.application

import com.benasher44.uuid.uuidFrom
import com.uramnoil.serverist.SessionId
import com.uramnoil.serverist.application.auth.SignInUseCaseInput
import com.uramnoil.serverist.application.auth.SignInUseCaseInputPort
import com.uramnoil.serverist.application.auth.SignInUseCaseOutput
import com.uramnoil.serverist.application.auth.SignInUseCaseOutputPort
import com.uramnoil.serverist.exceptions.BadRequestException
import com.uramnoil.serverist.exceptions.InternalServerErrorException
import io.github.aakira.napier.Napier
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.coroutines.CoroutineContext

class SignInUseCaseInteractor(
    private val coroutineContext: CoroutineContext,
    private val host: String,
    private val httpClient: HttpClient,
    private val sessionId: SessionId,
    private val outputPort: SignInUseCaseOutputPort,
) : SignInUseCaseInputPort {
    @Serializable
    data class Credential(val email: String, val password: String)

    @Serializable
    data class IdResponse(@SerialName("id") val id: String)

    override fun execute(input: SignInUseCaseInput) {
        val (email, password) = input
        CoroutineScope(coroutineContext).launch {
            Napier.d("fuga")
            val response = httpClient.request<HttpStatement>("$host/signin") {
                method = HttpMethod.Post
                contentType(ContentType.Application.Json)
                body = Credential(email, password)
            }.execute()
            val result = when (response.status) {
                HttpStatusCode.OK -> {
                    sessionId.sessionId = response.headers["Auth"]
                    val text = response.receive<String>()
                    Result.success(uuidFrom(text))
                }
                HttpStatusCode.BadRequest -> {
                    Result.failure(BadRequestException())
                }
                else -> {
                    Result.failure(InternalServerErrorException())
                }
            }

            outputPort.handle(SignInUseCaseOutput(result))
        }
    }
}
