package com.uramnoil.serverist.infrastructure.server.application

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.toInput
import com.apollographql.apollo.coroutines.await
import com.benasher44.uuid.uuidFrom
import com.uramnoil.serverist.CreateServerMutation
import com.uramnoil.serverist.application.server.CreateServerUseCaseInput
import com.uramnoil.serverist.application.server.CreateServerUseCaseInputPort
import com.uramnoil.serverist.application.server.CreateServerUseCaseOutput
import com.uramnoil.serverist.application.server.CreateServerUseCaseOutputPort
import io.github.aakira.napier.Napier
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext


@OptIn(ExperimentalCoroutinesApi::class)
class CreateServerUseCaseInteractor(
    private val apolloClient: ApolloClient,
    coroutineContext: CoroutineContext,
    private val outputPort: CreateServerUseCaseOutputPort,
) : CreateServerUseCaseInputPort, CoroutineScope by CoroutineScope(coroutineContext) {
    override fun execute(input: CreateServerUseCaseInput) {
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
                outputPort.handle(CreateServerUseCaseOutput(Result.failure(RuntimeException("Errors returned."))))
                return@launch
            }

            val data = createServerResult.data

            data ?: run {
                // Data is null
                outputPort.handle(CreateServerUseCaseOutput(Result.failure(IllegalStateException("No data returned."))))
                return@launch
            }

            val serversResult = runCatching {
                uuidFrom(data.createServer as String)
            }

            outputPort.handle(CreateServerUseCaseOutput(serversResult))
        }
    }
}