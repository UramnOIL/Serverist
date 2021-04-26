package com.uramnoil.serverist.domain.models.user

data class HashedPassword(val value: ByteArray) {
    init {
        if (value.count() > 255) {
            throw IllegalArgumentException("255バイト以下にしてください。")
        }
    }
}
