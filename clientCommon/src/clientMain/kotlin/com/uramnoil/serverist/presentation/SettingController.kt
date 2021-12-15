package com.uramnoil.serverist.presentation

import com.uramnoil.serverist.application.user.UpdateUserUseCaseInput
import com.uramnoil.serverist.application.user.UpdateUserUseCaseInputPort
import com.uramnoil.serverist.application.user.UpdateUserUseCaseOutputPort
import kotlin.coroutines.CoroutineContext

class SettingController(
    coroutineContext: CoroutineContext,
    updateUserUseCaseOutputPort: UpdateUserUseCaseOutputPort
) {
    private val updateUserUseCaseInputPort: UpdateUserUseCaseInputPort = TODO("DIコンテナの導入")

    fun updateUser(accountId: String, name: String, description: String) {
        updateUserUseCaseInputPort.execute(UpdateUserUseCaseInput(accountId, name, description))
    }
}