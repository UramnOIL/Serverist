package com.uramnoil.serverist.domain.serverist.models.user

data class Name(val value: String) {
    init {
        if (value.length > 31) {
            throw IllegalArgumentException("Strings longer than 31 characters are not allowed.")
        }
    }
}
