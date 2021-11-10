package com.uramnoil.serverist.domain.auth.kernel.model

data class Password(val value: String) {
    init {
        if (PasswordSpec.isSatisfiedBy(value)) {
            throw IllegalArgumentException("The password must be at least 8 characters long and must be alphanumeric.")
        }
    }
}
