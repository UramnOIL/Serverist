package com.uramnoil.serverist.domain.serverist.models.server

data class Port(val value: UShort) {
    init {
        if (value < 0u) {
            throw IllegalArgumentException("0未満は代入できません。")
        }
        if (value > 65535u) {
            throw IllegalArgumentException("65536以上は代入できません。")
        }
    }
}
