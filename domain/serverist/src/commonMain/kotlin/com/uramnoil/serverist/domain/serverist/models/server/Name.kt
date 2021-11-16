package com.uramnoil.serverist.domain.serverist.models.server

data class Name(val value: String) {
    init {
        // 空白のみの代入の禁止
        if (value.isBlank()) {
            throw IllegalArgumentException("Empty characters are not allowed.")
        }

        // 32文字以上の代入の禁止
        if (value.length > 31) {
            throw IllegalArgumentException("Only up to 31 characters are allowed.")
        }

        // タブ・改行文字の禁止
        val regex = Regex(pattern = "[\t\n\r]")
        if (regex.matches(value)) {
            throw IllegalArgumentException("No newline or tab characters are allowed.")
        }

        // 前後の空白の禁止
        if (value.trim() != value) {
            throw IllegalArgumentException("No spaces before or after.")
        }
    }
}
