package com.uramnoil.serverist.domain.models.unapproveduser

data class AccountId(val value: String) {
    init {
        if (value.length > 16) {
            throw IllegalArgumentException("16文字以下にしてください。")
        }
    }
}
