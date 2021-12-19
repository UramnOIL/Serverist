package com.uramnoil.serverist.presentation

import com.uramnoil.serverist.application.auth.SignInUseCaseInput
import com.uramnoil.serverist.application.auth.SignInUseCaseInputPort
import com.uramnoil.serverist.application.auth.SignUpUseCaseInput
import com.uramnoil.serverist.application.auth.SignUpUseCaseInputPort

class AuthController(
    private val signUpUseCaseInputPort: SignUpUseCaseInputPort,
    private val signInUseCaseInputPort: SignInUseCaseInputPort
) {
    fun signIn(email: String, password: String) {
        signInUseCaseInputPort.execute(SignInUseCaseInput(email, password))
    }

    fun signUp(email: String, password: String) {
        signUpUseCaseInputPort.execute(SignUpUseCaseInput(email, password))
    }
}