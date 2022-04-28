package com.uramnoil.serverist.serverist.domain.models.user

data class Name(val value: String) {
    init {
        require(value.length <= 31) { "Strings longer than 31 characters are not allowed." }
    }
}
