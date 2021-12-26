package com.uramnoil.serverist.presentation

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class AuthViewModel(
    private val mutableSignUpErrorStateFlow: MutableStateFlow<Throwable?>,
    private val mutableSignInErrorStateFlow: MutableStateFlow<Throwable?>,
    private val mutableActivateErrorStateFlow: MutableStateFlow<Throwable?>,
) {
    val signUpErrorStateFlow: StateFlow<Throwable?>
        get() = mutableSignUpErrorStateFlow

    val signInErrorStateFlow: StateFlow<Throwable?>
        get() = mutableSignInErrorStateFlow

    val activateErrorStateFlow: StateFlow<Throwable?>
        get() = mutableActivateErrorStateFlow
}