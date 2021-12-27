package com.uramnoil.serverist.domain.auth.unauthenticated.models

data class ActivationCode(val value: String) {
    init {
        if (value.length != 6) {
            throw IllegalArgumentException("The length of the activation code must be 6 characters.")
        }
        if (!value.all { it in '0'..'9' }) {
            throw IllegalArgumentException("All letters in the activation code must be numbers.")
        }
    }
}
