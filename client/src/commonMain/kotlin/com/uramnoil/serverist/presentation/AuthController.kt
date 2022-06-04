package com.uramnoil.serverist.presentation

import com.uramnoil.serverist.application.auth.*

class AuthController(
    private val signUpUseCaseInputPort: SignUpUseCaseInputPort,
    private val activateUseCaseInputPort: ActivateUseCaseInputPort,
    private val signInUseCaseInputPort: SignInUseCaseInputPort
) {
    fun signUp(email: String, password: String) {
        signUpUseCaseInputPort.execute(SignUpUseCaseInput(email, password))
    }

    fun activation(email: String, activationCode: String) {
        activateUseCaseInputPort.execute(ActivateUseCaseInput(email, activationCode))
    }

    fun signIn(email: String, password: String) {
        signInUseCaseInputPort.execute(SignInUseCaseInput(email, password))
    }
}
