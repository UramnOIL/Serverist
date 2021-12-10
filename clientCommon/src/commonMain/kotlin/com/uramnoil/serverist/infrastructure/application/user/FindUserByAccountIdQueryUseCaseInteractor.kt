package com.uramnoil.serverist.infrastructure.application.user

import com.uramnoil.serverist.application.user.FindUserByAccountIdQueryUseCaseInput
import com.uramnoil.serverist.application.user.FindUserByAccountIdQueryUseCaseInputPort
import com.uramnoil.serverist.application.user.FindUserByAccountIdQueryUseCaseOutputPort
import com.uramnoil.serverist.exceptions.BadRequestException
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class FindUserByAccountIdQueryUseCaseInteractor(
    private val httpClient: HttpClient,
    private val url: String,
    private val outputPort: FindUserByAccountIdQueryUseCaseOutputPort,
    coroutineContext: CoroutineContext
) : FindUserByAccountIdQueryUseCaseInputPort, CoroutineScope by CoroutineScope(coroutineContext) {
    override fun execute(input: FindUserByAccountIdQueryUseCaseInput) {
        val (id) = input
        launch {
            val result = kotlin.runCatching {
                val response = httpClient.post<HttpResponse>(url) {
                    body = """
                        query FindAllUsers {
                          findAllUsers() {
                            id
                          }
                        }
                    """.trimIndent()
                }
                if (response.status.value == 200) {
                    throw BadRequestException()
                }
            }
            //outputPort.handle(result)
        }
    }
}