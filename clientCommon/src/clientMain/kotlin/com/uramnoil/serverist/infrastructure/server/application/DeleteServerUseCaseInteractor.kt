package com.uramnoil.serverist.infrastructure.server.application

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.annotations.ApolloExperimental
import com.uramnoil.serverist.DeleteServerMutation
import com.uramnoil.serverist.application.server.DeleteServerUseCaseInput
import com.uramnoil.serverist.application.server.DeleteServerUseCaseInputPort
import com.uramnoil.serverist.application.server.DeleteServerUseCaseOutput
import com.uramnoil.serverist.application.server.DeleteServerUseCaseOutputPort
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

@OptIn(ExperimentalCoroutinesApi::class)
class DeleteServerUseCaseInteractor(
    coroutineContext: CoroutineContext,
    private val apolloClient: ApolloClient,
    private val outputPort: DeleteServerUseCaseOutputPort,
) : DeleteServerUseCaseInputPort, CoroutineScope by CoroutineScope(coroutineContext) {
    @OptIn(ApolloExperimental::class)
    override fun execute(input: DeleteServerUseCaseInput) {
        launch {
            val mutation = input.run {
                DeleteServerMutation(
                    id = input.id.toString()
                )
            }
            val deleteServerResult = apolloClient.mutation(mutation).execute()

            deleteServerResult.errors?.run {
                forEach {
                    // Napier.e(it.message)
                }
                outputPort.handle(DeleteServerUseCaseOutput(Result.failure(RuntimeException("Errors returned."))))
                return@launch
            }

            val data = deleteServerResult.data

            data ?: run {
                // Data is null
                outputPort.handle(DeleteServerUseCaseOutput(Result.failure(IllegalStateException("No data returned."))))
                return@launch
            }

            outputPort.handle(DeleteServerUseCaseOutput(Result.success(Unit)))
        }
    }
}
