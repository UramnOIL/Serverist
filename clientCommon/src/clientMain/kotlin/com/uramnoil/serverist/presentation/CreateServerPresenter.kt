package com.uramnoil.serverist.presentation

import com.uramnoil.serverist.application.server.CreateServerUseCaseOutput
import com.uramnoil.serverist.application.server.CreateServerUseCaseOutputPort

class CreateServerViewModel

class CreateServerPresenter(private val createServerViewModel: CreateServerViewModel) : CreateServerUseCaseOutputPort {
    override fun handle(output: CreateServerUseCaseOutput) {
    }
}