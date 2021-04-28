package com.uramnoil.serverist.domain.services

data class Password(val value: String) {
    init {
        if (PasswordSpec.isSatisfiedBy(value)) {
            throw IllegalArgumentException("パスワードは８文字以上の半角英数字を使用してください。")
        }
    }
}
