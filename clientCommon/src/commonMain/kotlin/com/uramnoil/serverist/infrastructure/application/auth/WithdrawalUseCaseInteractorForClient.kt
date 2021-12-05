package com.uramnoil.serverist.infrastructure.application.auth

import com.uramnoil.serverist.application.auth.WithdrawalUseCaseInputPortForClient
import com.uramnoil.serverist.application.auth.WithdrawalUseCaseOutputPort
import com.uramnoil.serverist.exceptions.BadRequestException
import com.uramnoil.serverist.exceptions.InternalServerErrorException
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.util.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class WithdrawalUseCaseInteractorForClient(
    private val client: HttpClient,
    private val host: String,
    private val outputPort: WithdrawalUseCaseOutputPort,
    private val coroutineContext: CoroutineContext,
) : WithdrawalUseCaseInputPortForClient {
    override fun execute() {
        CoroutineScope(coroutineContext).launch {
            val result = kotlin.runCatching {
                val response = client.post<HttpResponse>("$host/withdrawal")

                val message = response.content.toByteArray().decodeToString()

                when (response.status) {
                    HttpStatusCode.OK -> {}
                    HttpStatusCode.BadRequest -> {
                        throw BadRequestException(message)
                    }
                    else -> {
                        throw InternalServerErrorException(message)
                    }
                }
            }

            outputPort.handle(result)
        }
    }
}