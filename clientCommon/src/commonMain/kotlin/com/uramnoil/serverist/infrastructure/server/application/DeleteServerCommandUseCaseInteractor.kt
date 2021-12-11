package com.uramnoil.serverist.infrastructure.server.application

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.ApolloExperimental
import com.apollographql.apollo.coroutines.await
import com.uramnoil.serverist.DeleteServerMutation
import com.uramnoil.serverist.application.server.DeleteServerCommandUseCaseInput
import com.uramnoil.serverist.application.server.DeleteServerCommandUseCaseInputPort
import com.uramnoil.serverist.application.server.DeleteServerCommandUseCaseOutput
import com.uramnoil.serverist.application.server.DeleteServerCommandUseCaseOutputPort
import io.github.aakira.napier.Napier
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class DeleteServerCommandUseCaseInteractor(
    private val apolloClient: ApolloClient,
    coroutineContext: CoroutineContext,
    private val outputPort: DeleteServerCommandUseCaseOutputPort,
) : DeleteServerCommandUseCaseInputPort, CoroutineScope by CoroutineScope(coroutineContext) {
    @OptIn(ApolloExperimental::class)
    override fun execute(input: DeleteServerCommandUseCaseInput) {
        launch {
            val mutation = input.run {
                DeleteServerMutation(
                    id = input.id.toString()
                )
            }
            val deleteServerResult = apolloClient.mutate(mutation).await()

            deleteServerResult.errors?.run {
                forEach {
                    Napier.e(it.message)
                }
                outputPort.handle(DeleteServerCommandUseCaseOutput(Result.failure(RuntimeException("Errors returned."))))
                return@launch
            }

            val data = deleteServerResult.data

            data ?: run {
                // Data is null
                outputPort.handle(DeleteServerCommandUseCaseOutput(Result.failure(IllegalStateException("No data returned."))))
                return@launch
            }

            outputPort.handle(DeleteServerCommandUseCaseOutput(Result.success(Unit)))
        }
    }
}