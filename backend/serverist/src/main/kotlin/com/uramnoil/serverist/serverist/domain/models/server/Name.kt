package com.uramnoil.serverist.serverist.domain.models.server

data class Name(val value: String) {
    init {
        // 空白のみの代入の禁止
        require(value.isNotBlank()) { "Empty characters are not allowed." }

        // 32文字以上の代入の禁止
        require(value.length <= 31) { "Only up to 31 characters are allowed." }

        // タブ・改行文字の禁止
        val regex = Regex(pattern = "[\t\n\r]")
        require(!(regex.matches(value))) { "No newline or tab characters are allowed." } 

        // 前後の空白の禁止
        require(value.trim() == value) { "No spaces before or after." }
    }
}
