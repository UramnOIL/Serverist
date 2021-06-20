package com.uramnoil.serverist.domain.kernel.models.user

object PasswordSpec {
    fun isSatisfiedBy(password: String) =
        !Regex(pattern = """^(?=.*[a-z])(?=.*[0-9])[a-zA-Z0-9!"#$%&'()*+,\-./:;<=>?\[\\\]^_`{|}~]{8,}$""")
            .matches(password)
}