package com.uramnoil.serverist.domain.kernel.models

data class Password(val value: String) {
    init {
        if (com.uramnoil.serverist.domain.kernel.models.PasswordSpec.isSatisfiedBy(value)) {
            throw IllegalArgumentException("パスワードは８文字以上の半角英数字を使用してください。")
        }
    }
}
