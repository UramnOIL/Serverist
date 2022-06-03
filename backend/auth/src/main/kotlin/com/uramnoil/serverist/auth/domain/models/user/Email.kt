package com.uramnoil.serverist.auth.domain.models.user

/**
 * Email Value Object
 * RFC5322にほぼ準拠
 *
 * @param value                       Emailの文字列
 * @throws IllegalArgumentException    不正な値を渡された場合
 */
data class Email(val value: String) {
    init {
        require(value.length <= 255) { "It should be no more than 255 characters." }
        val regex = Regex(pattern = """^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$""")

        require(regex.matches(value)) { "Illegal form of email address." }
    }
}
