package com.uramnoil.serverist.infrastructure.application.authenticated

import com.benasher44.uuid.uuidFrom
import com.uramnoil.serverist.application.auth.LoginUseCaseInputPort
import com.uramnoil.serverist.application.auth.LoginUseCaseOutputPort
import com.uramnoil.serverist.exceptions.BadRequestException
import com.uramnoil.serverist.exceptions.InternalServerErrorException
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlin.coroutines.CoroutineContext

class LoginUseCaseInteractor(
    private val host: String,
    private val httpClient: HttpClient,
    private val outputPort: LoginUseCaseOutputPort,
    private val coroutineContext: CoroutineContext,
) : LoginUseCaseInputPort {
    @Serializable
    data class Credential(val email: String, val password: String)

    @Serializable
    data class IdResponse(@SerialName("id") val id: String)

    override fun execute(email: String, password: String) {
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

            outputPort.handle(result)
        }
    }
}