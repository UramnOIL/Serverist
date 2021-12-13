package com.uramnoil.serverist.infrastructure.user.application

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.coroutines.await
import com.uramnoil.serverist.UpdateUserMutation
import com.uramnoil.serverist.application.user.UpdateUserCommandUseCaseInput
import com.uramnoil.serverist.application.user.UpdateUserCommandUseCaseInputPort
import com.uramnoil.serverist.application.user.UpdateUserCommandUseCaseOutput
import com.uramnoil.serverist.application.user.UpdateUserCommandUseCaseOutputPort
import io.github.aakira.napier.Napier
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

/**
 *
 */
@OptIn(ExperimentalCoroutinesApi::class)
class UpdateUserUseCaseInteractor(
    private val apolloClient: ApolloClient,
    coroutineContext: CoroutineContext,
    private val outputPort: UpdateUserCommandUseCaseOutputPort,
) : UpdateUserCommandUseCaseInputPort, CoroutineScope by CoroutineScope(coroutineContext) {
    override fun execute(input: UpdateUserCommandUseCaseInput) {
        launch {
            val mutation = input.run {
                UpdateUserMutation(
                    accountId = accountId,
                    name = name,
                    description = description
                )
            }
            val response = apolloClient.mutate(mutation).await()

            // Error
            response.errors?.run {
                forEach {
                    Napier.e(it.message)
                }
                outputPort.handle(UpdateUserCommandUseCaseOutput(Result.failure(RuntimeException("Error returned."))))
            }

            val data = response.data
            data ?: run {
                // Data is null
                outputPort.handle(UpdateUserCommandUseCaseOutput(Result.failure(IllegalStateException("No data returned."))))
                return@launch
            }

            outputPort.handle(UpdateUserCommandUseCaseOutput(Result.success(Unit)))
        }
    }
}