package com.uramnoil.serverist.model.server

data class Description(val value: String) {
    init {
        if(value == "") {
            throw IllegalArgumentException("空文字は代入できません。")
        }
    }
}
