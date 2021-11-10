package com.uramnoil.serverist.domain.serverist.models.server

data class Host(val value: String?) {
    init {
        if (value == "") {
            throw IllegalArgumentException("Empty characters cannot be assigned.")
        }
        val ipRegex =
            Regex(pattern = """^(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\.){3}([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])$""")
        val domainRegex =
            Regex(pattern = """^([a-zA-Z0-9][a-zA-Z0-9-]*[a-zA-Z0-9]*\.)+[a-zA-Z]{2,}$""")

        if (value != null) {
            if (!(ipRegex.matches(value) || domainRegex.matches(value))) {
                throw IllegalArgumentException("Incorrect host format.")
            }
        }
    }
}
