package com.uramnoil.serverist.infrastructure.application.user.queries

import com.uramnoil.serverist.application.user.User
import com.uramnoil.serverist.application.user.queries.FindAllUsersQueryUseCaseInputPort
import com.uramnoil.serverist.application.user.queries.FindAllUsersQueryUseCaseOutputPort
import com.uramnoil.serverist.exceptions.BadRequestException
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.util.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlin.coroutines.CoroutineContext

class FindAllUsersQueryUseCaseInputPortInteractor(
    private val httpClient: HttpClient,
    private val url: String,
    private val outputPort: FindAllUsersQueryUseCaseOutputPort,
    coroutineContext: CoroutineContext
) : FindAllUsersQueryUseCaseInputPort, CoroutineScope by CoroutineScope(coroutineContext) {
    override fun execute() {
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
                Json.decodeFromString<List<User>>(response.content.toByteArray().toString())
            }
            outputPort.handle(result)
        }
    }
}