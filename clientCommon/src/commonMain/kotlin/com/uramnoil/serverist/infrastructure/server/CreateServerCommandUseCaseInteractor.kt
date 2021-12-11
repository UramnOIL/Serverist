package com.uramnoil.serverist.infrastructure.server

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.toInput
import com.apollographql.apollo.coroutines.await
import com.benasher44.uuid.uuidFrom
import com.uramnoil.serverist.CreateServerMutation
import com.uramnoil.serverist.application.server.CreateServerCommandUseCaseInput
import com.uramnoil.serverist.application.server.CreateServerCommandUseCaseInputPort
import com.uramnoil.serverist.application.server.CreateServerCommandUseCaseOutputPort
import io.github.aakira.napier.Napier
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class CreateServerCommandUseCaseInteractor(
    private val apolloClient: ApolloClient,
    coroutineContext: CoroutineContext,
    private val outputPort: CreateServerCommandUseCaseOutputPort,
) : CreateServerCommandUseCaseInputPort, CoroutineScope by CoroutineScope(coroutineContext) {
    override fun execute(input: CreateServerCommandUseCaseInput) {
        launch {
            val mutation = input.run {
                CreateServerMutation(
                    name,
                    host.toInput(),
                    port?.toInt().toInput(),
                    description
                )
            }
            val createServerResult = apolloClient.mutate(mutation).await()

            createServerResult.errors?.run {
                forEach {
                    Napier.e(it.message)
                }
                outputPort.handle(Result.failure(RuntimeException("Errors returned.")))
                return@launch
            }

            val data = createServerResult.data

            data ?: run {
                // Data is null
                outputPort.handle(Result.failure(IllegalStateException("No data returned.")))
                return@launch
            }

            val serversResult = runCatching {
                uuidFrom(data.createServer as String)
            }

            outputPort.handle(serversResult)
        }
    }
}