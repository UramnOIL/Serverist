package com.uramnoil.serverist.infrastructure.server.application

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.annotations.ApolloExperimental
import com.benasher44.uuid.uuidFrom
import com.uramnoil.serverist.FindAllServersQuery
import com.uramnoil.serverist.application.server.FindAllServersUseCaseInput
import com.uramnoil.serverist.application.server.FindAllServersUseCaseInputPort
import com.uramnoil.serverist.application.server.FindAllServersUseCaseOutput
import com.uramnoil.serverist.application.server.FindAllServersUseCaseOutputPort
import com.uramnoil.serverist.infrastructure.toApollo
import com.uramnoil.serverist.serverist.application.server.Server
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlin.coroutines.CoroutineContext

@OptIn(ApolloExperimental::class, ExperimentalCoroutinesApi::class)
class FindAllServersUseCaseInteractor(
    private val apolloClient: ApolloClient,
    coroutineContext: CoroutineContext,
    private val outputPort: FindAllServersUseCaseOutputPort
) : FindAllServersUseCaseInputPort, CoroutineScope by CoroutineScope(coroutineContext) {
    override fun execute(input: FindAllServersUseCaseInput) {
        launch {
            val query = input.run {
                FindAllServersQuery(
                    //page = PageRequest(limit, offset),
                    sort = sort.toApollo(),
                    orderBy = orderBy.toApollo()
                )
            }
            val response = apolloClient.query(query).execute()

            // GraphQL Error
            response.errors?.run {
                forEach {
                    //Napier.e(it.message)
                }
                outputPort.handle(FindAllServersUseCaseOutput(Result.failure(RuntimeException("Errors returned."))))
                return@launch
            }

            val data = response.data

            data ?: run {
                // Data is null
                outputPort.handle(FindAllServersUseCaseOutput(Result.failure(IllegalStateException("No data returned."))))
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

            outputPort.handle(FindAllServersUseCaseOutput(serversResult))
        }
    }
}