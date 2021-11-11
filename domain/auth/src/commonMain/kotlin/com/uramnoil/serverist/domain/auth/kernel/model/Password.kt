package com.uramnoil.serverist.domain.auth.kernel.model

data class Password(val value: String) {
    init {
        val regex = Regex(pattern = """^(?=.*[a-zA-Z])(?=.*[0-9])[a-zA-Z0-9!"#$%&'()*+,\-./:;<=>?\[\\\]^_`{|}~]{8,}$""")
        if (!regex.matches(value)) {
            throw IllegalArgumentException("The password must be at least 8 characters long and must be alphanumeric.")
        }
    }
}
