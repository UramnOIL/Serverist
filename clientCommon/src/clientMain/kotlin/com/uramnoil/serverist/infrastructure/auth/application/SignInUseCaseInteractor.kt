package com.uramnoil.serverist.infrastructure.auth.application

import com.benasher44.uuid.uuidFrom
import com.uramnoil.serverist.application.auth.SignInUseCaseInput
import com.uramnoil.serverist.application.auth.SignInUseCaseInputPort
import com.uramnoil.serverist.application.auth.SignInUseCaseOutput
import com.uramnoil.serverist.application.auth.SignInUseCaseOutputPort
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
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlin.coroutines.CoroutineContext

class SignInUseCaseInteractor(
    private val coroutineContext: CoroutineContext,
    private val host: String,
    private val httpClient: HttpClient,
    private val outputPort: SignInUseCaseOutputPort,
) : SignInUseCaseInputPort {
    @Serializable
    data class Credential(val email: String, val password: String)

    @Serializable
    data class IdResponse(@SerialName("id") val id: String)

    override fun execute(input: SignInUseCaseInput) {
        val (email, password) = input
        CoroutineScope(coroutineContext).launch {
            val response = httpClient.post<HttpResponse>("$host/login") {
                method = HttpMethod.Post
                contentType(ContentType.Application.Json)
                body = Credential(email, password)
            }
            val result = when (response.status) {
                HttpStatusCode.OK -> {
                    val text = response.receive<String>()
                    val (id) = Json.decodeFromString<IdResponse>(text)
                    Result.success(uuidFrom(id))
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