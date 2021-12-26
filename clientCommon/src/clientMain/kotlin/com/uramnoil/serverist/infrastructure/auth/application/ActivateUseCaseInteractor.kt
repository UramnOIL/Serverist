package com.uramnoil.serverist.infrastructure.auth.application

import com.uramnoil.serverist.application.auth.ActivateUseCaseInput
import com.uramnoil.serverist.application.auth.ActivateUseCaseInputPort
import com.uramnoil.serverist.application.auth.ActivateUseCaseOutput
import com.uramnoil.serverist.application.auth.ActivateUseCaseOutputPort
import io.ktor.client.HttpClient
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.statement.HttpStatement
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class ActivateUseCaseInteractor(
    coroutineContext: CoroutineContext,
    private val httpClient: HttpClient,
    private val host: String,
    private val outputPort: ActivateUseCaseOutputPort,
) : ActivateUseCaseInputPort, CoroutineScope by CoroutineScope(coroutineContext) {
    override fun execute(input: ActivateUseCaseInput) {
        launch {
            val (email, activationCode) = input
            val response = httpClient.post<HttpStatement>("$host/activate") {
                parameter("email", email)
                parameter("activationCode", activationCode)
            }.execute()

            val result = when (response.status) {
                HttpStatusCode.OK -> {
                    Result.success(Unit)
                }
                else -> {
                    Result.failure(IllegalArgumentException(""))
                }
            }

            outputPort.handle(ActivateUseCaseOutput(result))
        }
    }
}