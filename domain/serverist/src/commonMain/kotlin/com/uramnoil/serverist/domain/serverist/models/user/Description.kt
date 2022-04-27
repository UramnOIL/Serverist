package com.uramnoil.serverist.domain.serverist.models.user

data class Description(val value: String) {
    init {
        require (value.length < 256) { "255文字以下にしてください。" }
    }
}
