package com.uramnoil.serverist.domain.user.models

data class AccountId(val value: String) {
    init {
        if (value.length > 15) {
            throw IllegalArgumentException("15文字以下にしてください。")
        }
    }
}