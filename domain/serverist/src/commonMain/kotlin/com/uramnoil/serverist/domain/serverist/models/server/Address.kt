package com.uramnoil.serverist.domain.serverist.models.server

data class Address(val value: String?) {
    init {
        if (value == "") {
            throw IllegalArgumentException("Empty characters cannot be assigned.")
        }
        val ipRegex =
            Regex(pattern = """^(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\.){3}([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])$""")
        val hostRegex =
            Regex(pattern = """^(([a-zA-Z0-9]|[a-zA-Z0-9][a-zA-Z0-9\-]*[a-zA-Z0-9])\.)*([A-Za-z0-9]|[A-Za-z0-9][A-Za-z0-9\-]*[A-Za-z0-9])${'$'}""")

        if (value != null) {
            if (!(ipRegex.matches(value) || hostRegex.matches(value))) {
                throw IllegalArgumentException("Incorrect IP or host format.")
            }
        }
    }
}
