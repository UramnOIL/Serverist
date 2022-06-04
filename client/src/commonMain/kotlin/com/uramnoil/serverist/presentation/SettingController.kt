package com.uramnoil.serverist.presentation

import com.uramnoil.serverist.application.user.UpdateUserUseCaseInput
import com.uramnoil.serverist.application.user.UpdateUserUseCaseInputPort

class SettingController(
    private val updateUserUseCaseInputPort: UpdateUserUseCaseInputPort
) {
    fun updateUser(accountId: String, name: String, description: String) {
        updateUserUseCaseInputPort.execute(UpdateUserUseCaseInput(accountId, name, description))
    }
}
