package com.uramnoil.serverist.infrastructure.server.application

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.Optional
import com.benasher44.uuid.uuidFrom
import com.uramnoil.serverist.CreateServerMutation
import com.uramnoil.serverist.application.server.CreateServerUseCaseInput
import com.uramnoil.serverist.application.server.CreateServerUseCaseInputPort
import com.uramnoil.serverist.application.server.CreateServerUseCaseOutput
import com.uramnoil.serverist.application.server.CreateServerUseCaseOutputPort
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

@OptIn(ExperimentalCoroutinesApi::class)
class CreateServerUseCaseInteractor(
    coroutineContext: CoroutineContext,
    private val apolloClient: ApolloClient,
    private val outputPort: CreateServerUseCaseOutputPort,
) : CreateServerUseCaseInputPort, CoroutineScope by CoroutineScope(coroutineContext) {
    override fun execute(input: CreateServerUseCaseInput) {
        launch {
            val mutation = input.run {
                CreateServerMutation(
                    name,
                    Optional.presentIfNotNull(host),
                    Optional.presentIfNotNull(port?.toInt()),
                    description
                )
            }
            val createServerResult = apolloClient.mutation(mutation).execute()

            createServerResult.errors?.run {
                forEach {
                    // Napi.e(it.message)
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
                uuidFrom(data.createServer)
            }

            outputPort.handle(CreateServerUseCaseOutput(serversResult))
        }
    }
}
