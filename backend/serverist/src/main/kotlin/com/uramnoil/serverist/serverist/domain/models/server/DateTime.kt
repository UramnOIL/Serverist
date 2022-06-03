package com.uramnoil.serverist.serverist.domain.models.server

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant

data class DateTime(val value: Instant) {
    init {
        require(value <= Clock.System.now()) { "Only the past can be substituted." }
    }
}
