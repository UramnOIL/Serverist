package com.uramnoil.serverist.domain.serverist.models.server

data class Port(val value: UShort) {
    init {
        require(value >= 0u) { "0未満は代入できません。" }
        require(value <= 65535u) { "65536以上は代入できません。" }
    }
}
