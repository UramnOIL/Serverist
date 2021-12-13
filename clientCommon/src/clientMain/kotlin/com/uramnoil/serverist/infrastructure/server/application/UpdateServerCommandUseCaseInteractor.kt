package com.uramnoil.serverist.infrastructure.server.application

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.toInput
import com.apollographql.apollo.coroutines.await
import com.uramnoil.serverist.UpdateServerMutation
import com.uramnoil.serverist.application.server.UpdateServerCommandUseCaseInput
import com.uramnoil.serverist.application.server.UpdateServerCommandUseCaseInputPort
import com.uramnoil.serverist.application.server.UpdateServerCommandUseCaseOutput
import com.uramnoil.serverist.application.server.UpdateServerCommandUseCaseOutputPort
import io.github.aakira.napier.Napier
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext


@OptIn(ExperimentalCoroutinesApi::class)
class UpdateServerCommandUseCaseInteractor(
    private val apolloClient: ApolloClient,
    coroutineContext: CoroutineContext,
    private val outputPort: UpdateServerCommandUseCaseOutputPort,
) : UpdateServerCommandUseCaseInputPort, CoroutineScope by CoroutineScope(coroutineContext) {
    override fun execute(input: UpdateServerCommandUseCaseInput) {
        launch {
            val mutation = input.run {
                UpdateServerMutation(
                    id = id.toString(),
                    name = name,
                    host = host.toInput(),
                    port = port?.toInt().toInput(),
                    description = description
                )
            }
            val response = apolloClient.mutate(mutation).await()

            // Error
            response.errors?.run {
                forEach {
                    Napier.e(it.message)
                }
                outputPort.handle(UpdateServerCommandUseCaseOutput(Result.failure(RuntimeException("Error returned."))))
            }

            val data = response.data
            data ?: run {
                // Data is null
                outputPort.handle(UpdateServerCommandUseCaseOutput(Result.failure(IllegalStateException("No data returned."))))
                return@launch
            }

            outputPort.handle(UpdateServerCommandUseCaseOutput(Result.success(Unit)))
        }
    }
}