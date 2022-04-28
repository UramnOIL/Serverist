package com.uramnoil.serverist.serverist.domain.models.server

data class Description(val value: String) {
    init {
        require(value.length <= 255) { "255文字以下にしてください。" }
    }
}
