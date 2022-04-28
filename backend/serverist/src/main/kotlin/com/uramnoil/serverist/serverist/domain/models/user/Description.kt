package com.uramnoil.serverist.serverist.domain.models.user

data class Description(val value: String) {
    init {
        require(value.length < 256) { "255文字以下にしてください。" }
    }
}
