package com.uramnoil.serverist.infrastructure.user.application

import com.apollographql.apollo.ApolloClient
import com.uramnoil.serverist.application.user.FindUserByAccountIdQueryUseCaseInput
import com.uramnoil.serverist.application.user.FindUserByAccountIdQueryUseCaseInputPort
import com.uramnoil.serverist.application.user.FindUserByAccountIdQueryUseCaseOutputPort
import kotlinx.coroutines.CoroutineScope
import kotlin.coroutines.CoroutineContext

class FindUserByAccountIdQueryUseCaseInteractor(
    private val apolloClient: ApolloClient,
    private val outputPort: FindUserByAccountIdQueryUseCaseOutputPort,
    coroutineContext: CoroutineContext
) : FindUserByAccountIdQueryUseCaseInputPort, CoroutineScope by CoroutineScope(coroutineContext) {
    override fun execute(input: FindUserByAccountIdQueryUseCaseInput) {
        TODO("Not yet implemented")
    }
}