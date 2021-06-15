package com.uramnoil.serverist.domain.server.models

data class Port(val value: Int?) {
    init {
        value?.let {
            if (it < 0) {
                throw IllegalArgumentException("0未満は代入できません。")
            }
            if (it > 65535) {
                throw IllegalArgumentException("65536以上は代入できません。")
            }
        }
    }
}
