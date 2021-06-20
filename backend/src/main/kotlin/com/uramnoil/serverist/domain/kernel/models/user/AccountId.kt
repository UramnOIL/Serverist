package com.uramnoil.serverist.domain.kernel.models.user

data class AccountId(val value: String) {
    init {
        if (value.length > 15) {
            throw IllegalArgumentException("15文字以下にしてください。")
        }
    }
}
