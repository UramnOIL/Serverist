package com.uramnoil.serverist.domain.serverist.models.user

data class AccountId(val value: String) {
    init {
        // 15文字以内
        if (value.length > 15) {
            throw IllegalArgumentException("Longer than 15 characters Id is not allowed.")
        }

        // 3文字以上
        if (value.length < 3) {
            throw IllegalArgumentException("Shorter than 15 characters Id is not allowed.")
        }

        // 半角英数字 + _
        if (!Regex(pattern = """^[a-zA-Z0-9_]+$""").matches(value)) {
            throw IllegalArgumentException("Contains invalid characters.")
        }

        // 英字を含まない文字列の禁止
        if (Regex(pattern = """^[0-9_]+$""").matches(value)) {
            throw IllegalArgumentException("Patterns where all characters are _ or numbers are not allowed.")
        }
    }
}
