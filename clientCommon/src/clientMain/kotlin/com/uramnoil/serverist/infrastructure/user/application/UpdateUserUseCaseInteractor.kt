package com.uramnoil.serverist.infrastructure.user.application

import com.apollographql.apollo3.ApolloClient
import com.uramnoil.serverist.UpdateUserMutation
import com.uramnoil.serverist.application.user.UpdateUserUseCaseInput
import com.uramnoil.serverist.application.user.UpdateUserUseCaseInputPort
import com.uramnoil.serverist.application.user.UpdateUserUseCaseOutput
import com.uramnoil.serverist.application.user.UpdateUserUseCaseOutputPort
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
    private val outputPort: UpdateUserUseCaseOutputPort,
) : UpdateUserUseCaseInputPort, CoroutineScope by CoroutineScope(coroutineContext) {
    override fun execute(input: UpdateUserUseCaseInput) {
        launch {
            val mutation = input.run {
                UpdateUserMutation(
                    accountId = accountId,
                    userName = name,
                    description = description
                )
            }
            val response = apolloClient.mutation(mutation).execute()

            // Error
            response.errors?.run {
                forEach {
                    //Napier.e(it.message)
                }
                outputPort.handle(UpdateUserUseCaseOutput(Result.failure(RuntimeException("Error returned."))))
            }

            val data = response.data
            data ?: run {
                // Data is null
                outputPort.handle(UpdateUserUseCaseOutput(Result.failure(IllegalStateException("No data returned."))))
                return@launch
            }

            outputPort.handle(UpdateUserUseCaseOutput(Result.success(Unit)))
        }
    }
}