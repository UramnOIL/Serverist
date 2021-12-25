package com.uramnoil.serverist.infrastructure.server.application

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.Optional
import com.uramnoil.serverist.UpdateServerMutation
import com.uramnoil.serverist.application.server.UpdateServerUseCaseInput
import com.uramnoil.serverist.application.server.UpdateServerUseCaseInputPort
import com.uramnoil.serverist.application.server.UpdateServerUseCaseOutput
import com.uramnoil.serverist.application.server.UpdateServerUseCaseOutputPort
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

@OptIn(ExperimentalCoroutinesApi::class)
class UpdateServerUseCaseInteractor(
    coroutineContext: CoroutineContext,
    private val apolloClient: ApolloClient,
    private val outputPort: UpdateServerUseCaseOutputPort,
) : UpdateServerUseCaseInputPort, CoroutineScope by CoroutineScope(coroutineContext) {
    override fun execute(input: UpdateServerUseCaseInput) {
        launch {
            val mutation = input.run {
                UpdateServerMutation(
                    id = id.toString(),
                    serverName = name,
                    host = Optional.presentIfNotNull(host),
                    port = Optional.presentIfNotNull(port?.toInt()),
                    description = description
                )
            }
            val response = apolloClient.mutation(mutation).execute()

            // Error
            response.errors?.run {
                forEach {
                    // Napier.e(it.message)
                }
                outputPort.handle(UpdateServerUseCaseOutput(Result.failure(RuntimeException("Error returned."))))
            }

            val data = response.data
            data ?: run {
                // Data is null
                outputPort.handle(UpdateServerUseCaseOutput(Result.failure(IllegalStateException("No data returned."))))
                return@launch
            }

            outputPort.handle(UpdateServerUseCaseOutput(Result.success(Unit)))
        }
    }
}
