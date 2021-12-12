package com.uramnoil.serverist.infrastructure.server.application

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.ApolloExperimental
import com.apollographql.apollo.coroutines.await
import com.benasher44.uuid.uuidFrom
import com.uramnoil.serverist.FindAllServersQuery
import com.uramnoil.serverist.application.server.*
import com.uramnoil.serverist.infrastructure.toApollo
import com.uramnoil.serverist.type.PageRequest
import io.github.aakira.napier.Napier
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlin.coroutines.CoroutineContext

@OptIn(ApolloExperimental::class, ExperimentalCoroutinesApi::class)
class FindAllServersQueryUseCaseInteractor(
    private val apolloClient: ApolloClient,
    coroutineContext: CoroutineContext,
    private val outputPort: FindAllServersQueryUseCaseOutputPort
) : FindAllServersQueryUseCaseInputPort, CoroutineScope by CoroutineScope(coroutineContext) {
    override fun execute(input: FindAllServersQueryUseCaseInput) {
        launch {
            val query = input.run {
                FindAllServersQuery(
                    page = PageRequest(limit, offset),
                    sort = sort.toApollo(),
                    orderBy = orderBy.toApollo()
                )
            }
            val response = apolloClient.query(query).await()

            // GraphQL Error
            response.errors?.run {
                forEach {
                    Napier.e(it.message)
                }
                outputPort.handle(FindAllServersQueryUseCaseOutput(Result.failure(RuntimeException("Errors returned."))))
                return@launch
            }

            val data = response.data

            data ?: run {
                // Data is null
                outputPort.handle(FindAllServersQueryUseCaseOutput(Result.failure(IllegalStateException("No data returned."))))
                return@launch
            }

            val serversResult = runCatching {
                data.findServers.map {
                    it.run {
                        Server(
                            id = uuidFrom(id as String),
                            createdAt = Clock.System.now(), // FIXME: GraphQLの型を変更する
                            ownerId = uuidFrom(ownerId as String),
                            name = name,
                            host = host,
                            port = port,
                            description = description
                        )
                    }
                }
            }

            outputPort.handle(FindAllServersQueryUseCaseOutput(serversResult))
        }
    }
}