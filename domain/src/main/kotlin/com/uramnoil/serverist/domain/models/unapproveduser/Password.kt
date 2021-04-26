package com.uramnoil.serverist.domain.models.unapproveduser

data class Password(val value: String) {
    init {
        if (!Regex(pattern = """^(?=.*[a-z])(?=.*[0-9])[a-zA-Z0-9!"#$%&'()*+,\-./:;<=>?\[\\\]^_`{|}~]{8,}$""").matches(
                value
            )
        ) {
            throw IllegalArgumentException("パスワードは８文字以上の半角英数字を使用してください。")
        }
    }
}
