package com.uramnoil.serverist.infrastructure.auth.application

import com.uramnoil.serverist.application.auth.WithdrawalUseCaseInput
import com.uramnoil.serverist.application.auth.WithdrawalUseCaseInputPort
import com.uramnoil.serverist.application.auth.WithdrawalUseCaseOutput
import com.uramnoil.serverist.application.auth.WithdrawalUseCaseOutputPort
import com.uramnoil.serverist.exceptions.BadRequestException
import com.uramnoil.serverist.exceptions.InternalServerErrorException
import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpStatusCode
import io.ktor.util.toByteArray
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class WithdrawalUseCaseInteractor(
    private val coroutineContext: CoroutineContext,
    private val client: HttpClient,
    private val host: String,
    private val outputPort: WithdrawalUseCaseOutputPort,
) : WithdrawalUseCaseInputPort {
    override fun execute(input: WithdrawalUseCaseInput) {
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

            outputPort.handle(WithdrawalUseCaseOutput(result))
        }
    }
}
