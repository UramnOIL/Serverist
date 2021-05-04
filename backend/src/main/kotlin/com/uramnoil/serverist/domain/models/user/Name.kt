package com.uramnoil.serverist.domain.models.user

data class Name(val value: String) {
    init {
        if (value.length > 63) {
            throw IllegalArgumentException("63文字以下にしてください。")
        }
    }
}
