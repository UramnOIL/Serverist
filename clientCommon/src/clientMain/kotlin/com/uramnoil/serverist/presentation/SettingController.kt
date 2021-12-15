package com.uramnoil.serverist.presentation

import com.apollographql.apollo.ApolloClient
import com.uramnoil.serverist.application.user.UpdateUserUseCaseInput
import com.uramnoil.serverist.application.user.UpdateUserUseCaseInputPort
import com.uramnoil.serverist.application.user.UpdateUserUseCaseOutputPort
import kotlin.coroutines.CoroutineContext

class SettingController(
    coroutineContext: CoroutineContext,
    updateUserUseCaseInputFactory: (CoroutineContext, UpdateUserUseCaseOutputPort) -> UpdateUserUseCaseInputPort,
    updateUserUseCaseOutputPort: UpdateUserUseCaseOutputPort
) {
    val apolloClient: ApolloClient = TODO("後で")
    private val updateUserUseCaseInputPort =
        updateUserUseCaseInputFactory(coroutineContext, updateUserUseCaseOutputPort)

    fun updateUser(accountId: String, name: String, description: String) {
        updateUserUseCaseInputPort.execute(UpdateUserUseCaseInput(accountId, name, description))
    }
}