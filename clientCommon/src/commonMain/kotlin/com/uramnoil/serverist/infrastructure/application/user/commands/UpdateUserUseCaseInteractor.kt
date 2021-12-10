package com.uramnoil.serverist.infrastructure.application.user.commands

import com.uramnoil.serverist.application.user.UpdateUserCommandUseCaseInputPort
import com.uramnoil.serverist.application.user.UpdateUserCommandUseCaseOutputPort
import com.uramnoil.serverist.exceptions.BadRequestException
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

/**
 * ```
 * val apolloClient = ApolloClient(
 *     networkTransport = com.apollographql.apollo.network.http.ApolloHttpNetworkTransport(
 *         serverUrl = "https://your.domain/graphql/endpoint",
 *         headers = mapOf(
 *             "Accept" to "application/json",
 *             "Content-Type" to "application/json",
 *             ""
 *         )
 *     )
 * )
 * ```
 */
class UpdateUserUseCaseInteractor(
    private val httpClient: HttpClient,
    private val url: String,
    private val outputPort: UpdateUserCommandUseCaseOutputPort,
    private val coroutineContext: CoroutineContext
) : UpdateUserCommandUseCaseInputPort {
    override fun execute(accountId: String, name: String, description: String) {
        CoroutineScope(coroutineContext).launch {
            val result = kotlin.runCatching {
                val response = httpClient.post<HttpResponse>(url) {
                    body = """
                        mutation UpdateUser {
                          updateUser(name: $name, accountId: $accountId, description: $description ) {
                            id
                          }
                        }
                    """.trimIndent()
                }
                if (response.status.value == 200) {
                    throw BadRequestException()
                }
            }
            outputPort.handle(result)
        }
    }
}