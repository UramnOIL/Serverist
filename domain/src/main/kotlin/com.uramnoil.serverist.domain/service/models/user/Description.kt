package com.uramnoil.serverist.domain.service.models.user

data class Description(val value: String) {
    init {
        if (value.isEmpty()) {
            throw IllegalArgumentException("空文字は代入できません。")
        }
    }
}