package com.uramnoil.serverist.domain.auth.unauthenticated.models

data class AuthenticationCode(val value: String) {
    init {
        if (value.length != 31) throw IllegalArgumentException("The length of the code must be 31 characters")
    }
}
