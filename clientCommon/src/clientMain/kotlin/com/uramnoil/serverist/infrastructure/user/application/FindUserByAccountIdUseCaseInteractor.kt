package com.uramnoil.serverist.infrastructure.user.application

import com.apollographql.apollo3.ApolloClient
import com.uramnoil.serverist.application.user.FindUserByAccountIdUseCaseInput
import com.uramnoil.serverist.application.user.FindUserByAccountIdUseCaseInputPort
import com.uramnoil.serverist.application.user.FindUserByAccountIdUseCaseOutputPort
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlin.coroutines.CoroutineContext

@OptIn(ExperimentalCoroutinesApi::class)
class FindUserByAccountIdUseCaseInteractor(
    private val apolloClient: ApolloClient,
    private val outputPort: FindUserByAccountIdUseCaseOutputPort,
    coroutineContext: CoroutineContext
) : FindUserByAccountIdUseCaseInputPort, CoroutineScope by CoroutineScope(coroutineContext) {
    override fun execute(input: FindUserByAccountIdUseCaseInput) {
        TODO("Not yet implemented")
    }
}
