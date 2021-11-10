package com.uramnoil.serverist.domain.serverist.models.server

data class Name(val value: String) {
    init {
        if (value == "") {
            throw IllegalArgumentException("空文字は代入できません。")
        }
    }
}
