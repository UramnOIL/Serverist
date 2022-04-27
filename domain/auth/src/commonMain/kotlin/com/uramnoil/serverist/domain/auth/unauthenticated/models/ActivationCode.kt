package com.uramnoil.serverist.domain.auth.unauthenticated.models

data class ActivationCode(val value: String) {
    init {
        require (value.length == 6) { "The length of the activation code must be 6 characters." }
        require (value.all { it in '0'..'9' }) { "All letters in the activation code must be numbers." }
    }
}
