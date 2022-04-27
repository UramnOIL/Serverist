package com.uramnoil.serverist.domain.serverist.models.user

data class AccountId(val value: String) {
    init {
        // 3文字以上15文字以内
        require (value.length in 3..15) { "Strings of less than 3 characters or longer than 15 characters are not allowed." }

        // 半角英数字 + _
        require (Regex(pattern = """^\w+$""").matches(value)) { "Contains invalid characters." }

        // 英字を含まない文字列の禁止
        require (!(Regex(pattern = """^[\d_]+$""").matches(value))) { "Patterns where all characters are _ or numbers are not allowed." }
    }
}