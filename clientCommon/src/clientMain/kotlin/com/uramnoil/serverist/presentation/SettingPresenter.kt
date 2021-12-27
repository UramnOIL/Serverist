package com.uramnoil.serverist.presentation

import com.uramnoil.serverist.application.user.UpdateUserUseCaseOutput
import com.uramnoil.serverist.application.user.UpdateUserUseCaseOutputPort

class SettingPresenter(private val settingViewModel: SettingViewModel) : UpdateUserUseCaseOutputPort {
    override fun handle(output: UpdateUserUseCaseOutput) {
        TODO("Not yet implemented")
    }
}
