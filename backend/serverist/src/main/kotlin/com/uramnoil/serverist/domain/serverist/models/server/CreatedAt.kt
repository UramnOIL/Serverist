package com.uramnoil.serverist.domain.serverist.models.server

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant

data class CreatedAt(val value: Instant) {
    init {
        require(value <= Clock.System.now()) { "Only the present or past can be substituted." }
    }
}