package com.uramnoil.serverist.domain.serverist.models.server

data class Name(val value: String) {
    init {
        if (value.isBlank()) {
            throw IllegalArgumentException("Empty characters cannot be assigned.")
        }
        if (value.length > 31) {
            throw IllegalArgumentException("Only up to 31 characters can be substituted.")
        }
    }
}
