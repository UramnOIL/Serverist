package com.uramnoil.serverist.domain.serverist.models.server

data class Port(val value: Short) {
    init {
        if (value < 0) {
            throw IllegalArgumentException("0未満は代入できません。")
        }
        if (value > 65535) {
            throw IllegalArgumentException("65536以上は代入できません。")
        }
    }
}
