package com.uramnoil.serverist.domain.models.kernel.models

object PasswordSpec {
    fun isSatisfiedBy(password: String) =
        !Regex(pattern = """^(?=.*[a-z])(?=.*[0-9])[a-zA-Z0-9!"#$%&'()*+,\-./:;<=>?\[\\\]^_`{|}~]{8,}$""")
            .matches(password)
}