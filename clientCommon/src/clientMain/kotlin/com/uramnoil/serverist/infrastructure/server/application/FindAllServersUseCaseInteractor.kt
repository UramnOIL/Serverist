package com.uramnoil.serverist.infrastructure.server.application

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.annotations.ApolloExperimental
import com.apollographql.apollo3.api.ApolloResponse
import com.benasher44.uuid.uuidFrom
import com.uramnoil.serverist.FindAllServersQuery
import com.uramnoil.serverist.application.server.FindAllServersUseCaseInput
import com.uramnoil.serverist.application.server.FindAllServersUseCaseInputPort
import com.uramnoil.serverist.application.server.FindAllServersUseCaseOutput
import com.uramnoil.serverist.application.server.FindAllServersUseCaseOutputPort
import com.uramnoil.serverist.infrastructure.toApollo
import com.uramnoil.serverist.serverist.application.server.Server
import io.github.aakira.napier.Napier
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlinx.datetime.Instant
import kotlin.coroutines.CoroutineContext

@OptIn(ApolloExperimental::class, ExperimentalCoroutinesApi::class)
class FindAllServersUseCaseInteractor(
    coroutineContext: CoroutineContext,
    private val apolloClient: ApolloClient,
    private val outputPort: FindAllServersUseCaseOutputPort
) : FindAllServersUseCaseInputPort, CoroutineScope by CoroutineScope(coroutineContext + SupervisorJob()) {
    override fun execute(input: FindAllServersUseCaseInput) {
        launch {
            val result = runCatching {
                val query = input.run {
                    FindAllServersQuery(
                        // page = PageRequest(limit, offset),
                        sort = sort.toApollo(),
                        orderBy = orderBy.toApollo()
                    )
                }
                apolloClient.query(query).execute()
            }

            outputPort.handle(FindAllServersUseCaseOutput(transform(result)))
        }
    }

    private fun transform(result: Result<ApolloResponse<FindAllServersQuery.Data>>): Result<List<Server>> {
        val response = result.getOrElse { return Result.failure(it) }

        response.errors?.run {
            forEach {
                Napier.e(it.message)
            }
            return Result.failure(RuntimeException("Errors returned."))
        }

        val data = response.data

        data ?: run {
            // Data is null
            return Result.failure(IllegalStateException("No data returned."))
        }

        val servers = data.findServers.map {
            it.run {
                Server(
                    id = uuidFrom(id),
                    createdAt = Instant.fromEpochMilliseconds(createdAt as Long), // FIXME: GraphQLの型を変更する
                    ownerId = uuidFrom(ownerId),
                    name = name,
                    host = host,
                    port = port,
                    description = description
                )
            }
        }

        return Result.success(servers)
    }
}
